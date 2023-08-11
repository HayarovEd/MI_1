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
) : ViewModel() {
    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private var _lastState = MutableStateFlow<StatusApplication>(StatusApplication.Loading)
    init {
       // loadData()
    }

    fun loadData(link: String) {

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
                    appsFlyerDeeplink = sharedKeeper.getAppsFlyerConversion()
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
            viewModelScope.launch {
                service.getMyTrackerDeeplink { deeplink ->
                    _state.value.copy(
                        trackerDeeplink = deeplink
                    )
                        .updateStateUI()
                }
            }
            /*viewModelScope.launch {
                service.getAppsFlyerDeeplink { deeplink ->
                    _state.value.copy(
                        appsFlyerDeeplink = deeplink
                    )
                        .updateStateUI()
                }
            }*/
            getRemoteConfig()
            getSub1()
            getFirstSub2()
            getSub3()
            getSub5()
            loadDbData(link = link)
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
                    getSub2()
                    val completeUrl =
                        "${mainEvent.urlOffer}&aff_sub1=${_state.value.affsub1Unswer}&aff_sub2=${_state.value.affsub2Unswer}&aff_sub3=${_state.value.affsub3Unswer}&aff_sub5=${_state.value.affsub5Unswer}"
                    Log.d("AAAAAA", "url $completeUrl")
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
                        statusApplication = StatusApplication.Web(completeUrl),
                    )
                        .updateStateUI()
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
            val currentFireBaseToken = _state.value.fireBaseToken
            Log.d("AAAAAA", "currentFireBaseToken $currentFireBaseToken")
            val currentGaid = _state.value.gaid
            val currentMyTrackerId = _state.value.instanceIdMyTracker
            val currentAppsFlyerId = _state.value.instanceIdAppsFlyer
            when (val result = repositoryAnalytic.getSub1(
                applicationToken = APY_KEY,
                userId = currentGaid ?: "",
                appMetricaId = APP_METRICA,
                appsflyer = currentAppsFlyerId ?: "",
                firebaseToken = currentFireBaseToken ?: "",
                myTrackerId = currentMyTrackerId ?: ""
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
            val currentFireBaseToken = _state.value.fireBaseToken
            val currentGaid = _state.value.gaid
            val currentMyTrackerId = _state.value.instanceIdMyTracker
            val currentAppsFlyerId = _state.value.instanceIdAppsFlyer
            when (val result = repositoryAnalytic.getSub3(
                applicationToken = APY_KEY,
                userId = currentGaid ?: "",
                appMetricaId = APP_METRICA,
                appsflyer = currentAppsFlyerId ?: "",
                firebaseToken = currentFireBaseToken ?: "",
                myTrackerId = currentMyTrackerId ?: ""
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
            val currentGaid = _state.value.gaid
            when (val result = repositoryAnalytic.getSub5(
                applicationToken = APY_KEY,
                userId = currentGaid ?: "",
                gaid = currentGaid ?: ""
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
            delay(2000)
            val currentGaid = _state.value.gaid
            when (val result = repositoryAnalytic.getSub2(
                applicationToken = APY_KEY,
                userId = currentGaid ?: "",
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
                    _state.value.copy(
                        affsub2Unswer = result.data?.affsub2 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getSub2() {
        viewModelScope.launch {
            delay(2000)
            val currentGaid = _state.value.gaid
            val currentMyTracker = _state.value.trackerDeeplink
            val currentAppsFlyer = _state.value.appsFlyerDeeplink
            if (currentMyTracker.isNullOrBlank() && currentAppsFlyer.isNullOrBlank()) {
                when (val result = repositoryAnalytic.getSub2(
                    applicationToken = APY_KEY,
                    userId = currentGaid ?: "",
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
                        _state.value.copy(
                            affsub2Unswer = result.data?.affsub2 ?: ""
                        )
                            .updateStateUI()
                    }
                }
            } else if (currentMyTracker != null) {
                when (val result = repositoryAnalytic.getSub2(
                    applicationToken = APY_KEY,
                    userId = currentGaid ?: "",
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
                        _state.value.copy(
                            affsub2Unswer = result.data?.affsub2 ?: ""
                        )
                            .updateStateUI()
                    }
                }
            } else if (currentAppsFlyer != null) {
                when (val result = repositoryAnalytic.getSub2(
                    applicationToken = APY_KEY,
                    userId = currentGaid ?: "",
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
                        _state.value.copy(
                            affsub2Unswer = result.data?.affsub2 ?: ""
                        )
                            .updateStateUI()
                    }
                }
            }
        }
    }

    private fun loadDbData(link: String) {

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
                                if (link.isBlank()||link==" ") {
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
                                } else {
                                    delay(1000)
                                    val statusApplication = link.setStatusByPush(
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
        Log.d("AAAAAA", "sendingOffer $sendingData")
    }

    private fun sendFromListOffers(url: String, parameter:String) {
        val sendingData = mapOf(
            URL to url
        )
        YandexMetrica.reportEvent(parameter, sendingData)
        Log.d("AAAAAA", "sendingData $sendingData")
        MyTracker.trackEvent(parameter, sendingData)
        service.sendAppsFlyerEvent(
            key = parameter,
            content = sendingData
        )
    }
}