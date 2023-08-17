package lo.zaemtoperson.gola.presentation


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lo.zaemtoperson.gola.data.ACTUAL_BACKEND_NULL
import lo.zaemtoperson.gola.data.APP_METRICA
import lo.zaemtoperson.gola.data.APY_KEY
import lo.zaemtoperson.gola.data.BACKEND_UNAVAILABLE
import lo.zaemtoperson.gola.data.CARDS
import lo.zaemtoperson.gola.data.CREDITS
import lo.zaemtoperson.gola.data.EXTERNAL_LINK
import lo.zaemtoperson.gola.data.ITEM_ID
import lo.zaemtoperson.gola.data.LOANS
import lo.zaemtoperson.gola.data.MORE_DETAILS
import lo.zaemtoperson.gola.data.OFFER_WALL
import lo.zaemtoperson.gola.data.REQUEST_DATE
import lo.zaemtoperson.gola.data.REQUEST_DB
import lo.zaemtoperson.gola.data.Resource.Error
import lo.zaemtoperson.gola.data.Resource.Success
import lo.zaemtoperson.gola.data.URL
import lo.zaemtoperson.gola.data.setStatusByPush
import lo.zaemtoperson.gola.domain.AppWorker
import lo.zaemtoperson.gola.domain.RepositoryAnalytic
import lo.zaemtoperson.gola.domain.RepositoryServer
import lo.zaemtoperson.gola.domain.Service
import lo.zaemtoperson.gola.domain.SharedKepper
import lo.zaemtoperson.gola.domain.model.StatusApplication
import lo.zaemtoperson.gola.domain.model.StatusApplication.Connect
import lo.zaemtoperson.gola.domain.model.StatusApplication.Mock
import lo.zaemtoperson.gola.domain.model.StatusApplication.NoConnect
import lo.zaemtoperson.gola.domain.model.TypeCard
import lo.zaemtoperson.gola.domain.model.basedto.BaseState
import lo.zaemtoperson.gola.domain.model.basedto.Card
import lo.zaemtoperson.gola.domain.model.basedto.CardsCredit
import lo.zaemtoperson.gola.domain.model.basedto.CardsDebit
import lo.zaemtoperson.gola.domain.model.basedto.CardsInstallment
import lo.zaemtoperson.gola.presentation.MainEvent.OnChangeBaseState
import lo.zaemtoperson.gola.presentation.MainEvent.Reconnect

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: Service,
    private val sharedKeeper: SharedKepper,
    private val repositoryAnalytic: RepositoryAnalytic,
    private val repositoryServer: RepositoryServer,
    private val appWorker: AppWorker
) : ViewModel() {
    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private var _lastState = MutableStateFlow<StatusApplication>(StatusApplication.Loading)
    private val _myTracker = MutableStateFlow("")
    private val _link = MutableStateFlow("")
    init {
        loadData()
    }


    fun loadDeeplink(deeplink: String) {
        _myTracker.value = deeplink
    }

    fun loadlink(link: String) {
        _link.value = link
    }

    private fun loadData() {
        if (service.checkedInternetConnection()) {
            viewModelScope.launch {
                val appMetrika = service.appMetrika
                val instanceIdMyTracker =
                    if (sharedKeeper.getMyTrackerInstanceId().isNullOrBlank()) {
                        val instance = service.instanceIdMyTracker
                        sharedKeeper.setMyTrackerInstanceId(instance)
                        instance
                    } else {
                    sharedKeeper.getMyTrackerInstanceId()
                }
                val sim = service.getSimCountryIso()
                val isRoot = service.isRootedOne()
                val locale = service.getCurrentLocale()
                val deviceId = service.getDeviceAndroidId()
                val sharedFireBaseToken = sharedKeeper.getFireBaseToken()
                if (sharedFireBaseToken.isNullOrBlank()) {
                    service.getFirebaseMessagingToken { token ->
                        _state.value.copy(
                            fireBaseToken = token
                        )
                            .updateStateUI()
                        sharedKeeper.setFireBaseToken(token ?: "")
                    }
                } else {
                    _state.value.copy(
                        fireBaseToken = sharedFireBaseToken
                    )
                        .updateStateUI()
                }
                val version = service.getApplicationVersion()

                val instanceIdAppsFlyer = sharedKeeper.getAppsFlyerInstanceId()
                _state.value.copy(
                    appMetrica = appMetrika,
                    sim = sim,
                    instanceIdMyTracker = instanceIdMyTracker,
                    instanceIdAppsFlyer = instanceIdAppsFlyer,
                    isRoot = isRoot,
                    locale = locale,
                    deviceId = deviceId,
                    versionApplication = version,
                )
                    .updateStateUI()
            }
            val sharedYandexMetrica = sharedKeeper.getYandexMetricaDeviceId()
            if (sharedYandexMetrica.isNullOrBlank()) {
                service.getYandexMetricaDeviceId {
                    _state.value.copy(
                        yandexMetricaDeviceId = it
                    )
                        .updateStateUI()
                    sharedKeeper.setYandexMetricaDeviceId(it?:"")
                }
            } else {
                _state.value.copy(
                    yandexMetricaDeviceId = sharedYandexMetrica
                )
                    .updateStateUI()
            }
            viewModelScope.launch(Dispatchers.IO) {
                val gaid = service.getGAID()
                _state.value.copy(
                    gaid = gaid,
                )
                    .updateStateUI()
            }
            getRemoteConfig()
            getSub1()
            if (sharedKeeper.getSub2().isNullOrBlank()) {
                getFirstSub2()
            }
            getSub3()
            getSub5()
            loadDbData()
        } else {
            _state.value.copy(
                statusApplication = NoConnect
            )
                .updateStateUI()
        }
    }

    private fun MainState.updateStateUI() {
        _state.update {
            this
        }
    }

    fun onEvent(mainEvent: MainEvent) {
        when (mainEvent) {
            Reconnect -> {
                if (service.checkedInternetConnection()) {
                    _state.value.copy(
                        statusApplication = _lastState.value
                    )
                        .updateStateUI()
                } else {
                    _state.value.copy(
                        statusApplication = NoConnect,
                    )
                        .updateStateUI()
                }
            }

            is OnChangeBaseState -> {
                _state.value.copy(
                    statusApplication = Connect(mainEvent.baseState),
                )
                    .updateStateUI()
            }

            is MainEvent.OnChangeTypeCard -> {
                _state.value.copy(
                    statusApplication = Connect(BaseState.Cards(
                        mainEvent.typeCard)),
                )
                    .updateStateUI()
            }

            is MainEvent.OnChangeStatusApplication -> {
                _state.value.copy(
                    statusApplication = mainEvent.statusApplication,
                )
                    .updateStateUI()
            }

            is MainEvent.OnGoToWeb -> {
                _lastState.value = _state.value.statusApplication
                _state.value.copy(
                    statusApplication = StatusApplication.Loading,
                )
                    .updateStateUI()
                if (service.checkedInternetConnection()) {
                    //getSub2()
                    viewModelScope.launch {
                        delay(2000)
                        val completeUrl =
                            "${mainEvent.urlOffer}&aff_sub1=${_state.value.affsub1Unswer}&aff_sub2=${_state.value.affsub2Unswer}&aff_sub3=${_state.value.affsub3Unswer}&aff_sub5=${_state.value.affsub5Unswer}"
                        Log.d("ASDFGH", "url $completeUrl")
                        when (val lastState = _lastState.value) {
                            is Connect -> {
                                sendGoToOffer(
                                    url = completeUrl,
                                    parameter = OFFER_WALL
                                )
                                when (lastState.baseState) {
                                    is BaseState.Cards -> {
                                        sendFromListOffers(
                                            url = completeUrl,
                                            parameter = CARDS
                                        )
                                    }
                                    BaseState.Credits -> {
                                        sendFromListOffers(
                                            url = completeUrl,
                                            parameter = CREDITS
                                        )
                                    }
                                    BaseState.Loans -> {
                                        sendFromListOffers(
                                            url = completeUrl,
                                            parameter = LOANS
                                        )
                                    }
                                }
                            }
                            is StatusApplication.Info -> {}
                            StatusApplication.Loading -> {}
                            Mock -> {}
                            NoConnect -> {}
                            is StatusApplication.Offer -> {
                                sendGoToOffer(
                                    url = completeUrl,
                                    parameter = MORE_DETAILS
                                )
                            }
                            is StatusApplication.Web -> { }
                        }
                        _state.value.copy(
                            statusApplication = StatusApplication.Web(
                                url = completeUrl,
                                offerName = mainEvent.nameOffer),
                        )
                            .updateStateUI()
                    }
                } else {
                    _state.value.copy(
                        statusApplication = NoConnect,
                    )
                        .updateStateUI()
                }

            }
        }
    }

    private fun getRemoteConfig() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { resultListener ->
                if (resultListener.isSuccessful) {
                    val result = remoteConfig.getString("color")
                    _state.value.copy(
                        colorFb = result
                    )
                        .updateStateUI()
                }
            }
    }

    private fun getSub1() {
        viewModelScope.launch {
            delay(2000)
            when (val result = repositoryAnalytic.getSub1(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                appMetricaId = _state.value.yandexMetricaDeviceId?:"",
                appsflyer = _state.value.instanceIdAppsFlyer ?: "",
                firebaseToken = _state.value.fireBaseToken ?: "",
                myTrackerId = _state.value.instanceIdMyTracker ?: ""
            )) {
                is Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }
                is Success -> {
                    _state.value.copy(
                        affsub1Unswer = result.data?.affsub1?:""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getSub3() {
        viewModelScope.launch {
            delay(2000)
            when (val result = repositoryAnalytic.getSub3(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                appMetricaId = APP_METRICA,
                appsflyer = _state.value.instanceIdAppsFlyer ?: "",
                firebaseToken = _state.value.fireBaseToken ?: "",
                myTrackerId = _state.value.instanceIdMyTracker ?: ""
            )) {
                is Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Success -> {
                    _state.value.copy(
                        affsub3Unswer = result.data?.affsub3 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getSub5() {
        viewModelScope.launch {
            delay(2000)
            when (val result = repositoryAnalytic.getSub5(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                gaid = _state.value.gaid ?: ""
            )) {
                is Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Success -> {
                    _state.value.copy(
                        affsub5Unswer = result.data?.affsub5 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getFirstSub2() {
        viewModelScope.launch {
            delay(1000)
            when (val result = repositoryAnalytic.getSub2(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                appsflyer = "",
                myTracker = ""
            )) {
                is Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Success -> {
                    val sub2 = result.data?.affsub2
                    Log.d("ASDFGH", "affsub2UnswerEmpty $sub2")
                    _state.value.copy(
                        affsub2UnswerEmpty = sub2?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getSub2(currentMyTracker: String, currentAppsFlyer: String) {
        viewModelScope.launch {
            if (currentMyTracker.isNotBlank()) {
                when (val result = repositoryAnalytic.getSub2(
                    applicationToken = APY_KEY,
                    userId = _state.value.gaid ?: "",
                    appsflyer = "",
                    myTracker = currentMyTracker
                )) {
                    is Error -> {
                        _state.value.copy(
                            message = result.message ?: "unknown error"
                        )
                            .updateStateUI()
                    }

                    is Success -> {
                        Log.d("ASDFGH", "currentMyTracker $currentMyTracker")
                        val affsub2Unswer = result.data?.affsub2 ?: ""
                        Log.d("ASDFGH", "affsub2UnswerMT $affsub2Unswer")
                        _state.value.copy(
                            affsub2UnswerMT = affsub2Unswer
                        )
                            .updateStateUI()
                    }
                }
            }
            if (currentAppsFlyer.isNotBlank()) {
                when (val result = repositoryAnalytic.getSub2(
                    applicationToken = APY_KEY,
                    userId = _state.value.gaid ?: "",
                    appsflyer = currentAppsFlyer,
                    myTracker = ""
                )) {
                    is Error -> {
                        _state.value.copy(
                            message = result.message ?: "unknown error"
                        )
                            .updateStateUI()
                    }

                    is Success -> {
                        val affsub2Unswer = result.data?.affsub2 ?: ""
                        Log.d("ASDFGH", "affsub2UnswerAF $affsub2Unswer")
                        _state.value.copy(
                            affsub2UnswerAF = affsub2Unswer
                        )
                            .updateStateUI()
                    }
                }
            }
        }
    }

    private fun loadDbData() {

        viewModelScope.launch {
            delay(2000)
            val currentGaid = _state.value.gaid ?: ""
            when (val folder = repositoryServer.getFolder(
                sim = _state.value.sim ?: "",
                colorFb = _state.value.colorFb,
                deviceId = _state.value.deviceId ?: "",
                fbKey = _state.value.fireBaseToken ?: "",
                gaid = currentGaid,
                instanceMyTracker = _state.value.instanceIdMyTracker ?: "",
                local = _state.value.locale,
                metrikaKey = _state.value.appMetrica,
                root = if (_state.value.isRoot) "granted" else "null",
                version = _state.value.versionApplication ?: ""
            )) {
                is Error -> {
                    _state.value.copy(
                        message = folder.message ?: "unknown error",
                        statusApplication = Mock,
                    )
                        .updateStateUI()
                }

                is Success -> {
                    if (folder.data?.folder.isNullOrBlank() || folder.data?.folder == "null") {
                        YandexMetrica.reportEvent(ACTUAL_BACKEND_NULL, currentGaid)
                        MyTracker.trackEvent(ACTUAL_BACKEND_NULL, mapOf(ACTUAL_BACKEND_NULL to currentGaid))
                        service.sendAppsFlyerEvent(
                            key = ACTUAL_BACKEND_NULL,
                            content = mapOf(ACTUAL_BACKEND_NULL to currentGaid)
                        )
                        _state.value.copy(
                            statusApplication = Mock,
                        )
                            .updateStateUI()
                    } else {
                        val db = folder.data?.folder?.let { repositoryServer.getDataDb(it) }
                        YandexMetrica.reportEvent(REQUEST_DB, currentGaid)
                        MyTracker.trackEvent(REQUEST_DB, mapOf(REQUEST_DB to currentGaid))
                        service.sendAppsFlyerEvent(
                            key = REQUEST_DB,
                            content = mapOf(REQUEST_DB to currentGaid)
                        )
                        when (db) {
                            is Error -> {
                                YandexMetrica.reportEvent(BACKEND_UNAVAILABLE, currentGaid)
                                MyTracker.trackEvent(BACKEND_UNAVAILABLE, mapOf(BACKEND_UNAVAILABLE to currentGaid))
                                service.sendAppsFlyerEvent(
                                    key = BACKEND_UNAVAILABLE,
                                    content = mapOf(BACKEND_UNAVAILABLE to currentGaid)
                                )
                                _state.value.copy(
                                    statusApplication = Mock,
                                )
                                    .updateStateUI()
                            }

                            is Success -> {
                                collectCards(db.data?.cards)
                                Log.d("SDFGT", "sert ${db.data?.loans?.get(3)?.id}")
                                if (_link.value.isBlank()||_link.value==" ") {
                                    val statusApplication = if (!db.data?.loans.isNullOrEmpty()) {
                                        Connect(BaseState.Loans)
                                    } else if (!db.data?.credits.isNullOrEmpty()) {
                                        Connect(BaseState.Credits)
                                    } else {
                                        val typeCard = if (_state.value.creditCards.isNotEmpty()) {
                                            TypeCard.CardCredit
                                        } else if (_state.value.debitCards.isNotEmpty()) {
                                            TypeCard.CardDebit
                                        } else TypeCard.CardInstallment
                                        Connect(BaseState.Cards(typeCard))
                                    }
                                    _state.value.copy(
                                        statusApplication = statusApplication,
                                        dbData = db.data,
                                    )
                                        .updateStateUI()
                                    val sharedSub2 = sharedKeeper.getSub2()
                                    Log.d("ASDFGH", "myTracker view model1 -  ${_myTracker.value}")
                                    val tempSub2 =
                                        if (!sharedSub2.isNullOrBlank()) sharedSub2 else {
                                            delay(4000)
                                            //val myTracker = appWorker.myTracker
                                            Log.d("ASDFGH", "myTracker view model2 -  ${_myTracker.value}")
                                            val appsFlayer = appWorker.appsFlyer
                                            Log.d("ASDFGH", "appsFlayer view model -  $appsFlayer")
                                            getSub2(
                                                currentAppsFlyer = appsFlayer,
                                                currentMyTracker = _myTracker.value
                                            )
                                            if (_state.value.affsub2UnswerAF.isBlank() && _state.value.affsub2UnswerMT.isBlank()) {
                                                sharedKeeper.setSub2(_state.value.affsub2UnswerEmpty)
                                                _state.value.affsub2UnswerEmpty
                                            } else if (_state.value.affsub2UnswerAF.isBlank()) {
                                                sharedKeeper.setSub2(_state.value.affsub2UnswerMT)
                                                _state.value.affsub2UnswerMT
                                            } else if (_state.value.affsub2UnswerMT.isBlank()) {
                                                sharedKeeper.setSub2(_state.value.affsub2UnswerAF)
                                                _state.value.affsub2UnswerAF
                                            } else {
                                                if (_state.value.affsub2UnswerAF == _state.value.affsub2UnswerEmpty) {
                                                    sharedKeeper.setSub2(_state.value.affsub2UnswerMT)
                                                    _state.value.affsub2UnswerMT
                                                } else {
                                                    sharedKeeper.setSub2(_state.value.affsub2UnswerAF)
                                                    _state.value.affsub2UnswerAF
                                                }
                                            }
                                        }

                                    _state.value.copy(
                                        affsub2Unswer = tempSub2
                                    )
                                        .updateStateUI()
                                } else {
                                    delay(1000)
                                    val statusApplication = _link.value.setStatusByPush(
                                        loans = db.data?.loans?: emptyList(),
                                        credits = db.data?.credits?: emptyList(),
                                        creditCards = _state.value.creditCards,
                                        debitCards = _state.value.debitCards,
                                        installmentCards = _state.value.installmentCards
                                    )
                                    _state.value.copy(
                                        statusApplication = statusApplication,
                                        dbData = db.data,
                                    )
                                        .updateStateUI()
                                    delay(1000)
                                }
                            }

                            null -> {
                                _state.value.copy(
                                    statusApplication = Mock,
                                )
                                    .updateStateUI()
                            }
                        }
                        val currentDate =
                            folder.data?.let { repositoryServer.getCurrentDate(it.folder) }
                        YandexMetrica.reportEvent(REQUEST_DATE, currentGaid)
                        MyTracker.trackEvent(REQUEST_DATE, mapOf(REQUEST_DATE to currentGaid))
                        service.sendAppsFlyerEvent(
                            key = REQUEST_DATE,
                            content = mapOf(REQUEST_DATE to currentGaid)
                        )
                    }
                }
            }
        }
    }

    private fun collectCards(allCards: List<Card>?) {
        val creditCards = mutableListOf<CardsCredit>()
        val debitCards = mutableListOf<CardsDebit>()
        val installmentCards = mutableListOf<CardsInstallment>()
        allCards?.forEach { card ->
            creditCards.addAll(card.cardsCredit)
            debitCards.addAll(card.cardsDebit)
            installmentCards.addAll(card.cardsInstallment)
        }
        _state.value.copy(
            creditCards = creditCards,
            debitCards = debitCards,
            installmentCards = installmentCards
        )
            .updateStateUI()
    }

    private fun sendGoToOffer(url: String, parameter:String) {
        val sendingData = mapOf(
            ITEM_ID to parameter,
            URL to url
        )
        YandexMetrica.reportEvent(EXTERNAL_LINK, sendingData)
        MyTracker.trackEvent(EXTERNAL_LINK)
        service.sendAppsFlyerEvent(
            key = EXTERNAL_LINK,
            content = sendingData
        )
    }

    private fun sendFromListOffers(url: String, parameter:String) {
        val sendingData = mapOf(
            URL to url
        )
        YandexMetrica.reportEvent(parameter, sendingData)
        MyTracker.trackEvent(parameter, sendingData)
        service.sendAppsFlyerEvent(
            key = parameter,
            content = sendingData
        )
    }
}