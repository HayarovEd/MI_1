package lo.zaemtoperson.gola.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lo.zaemtoperson.gola.data.APP_METRICA
import lo.zaemtoperson.gola.data.APY_KEY
import lo.zaemtoperson.gola.data.Resource.Error
import lo.zaemtoperson.gola.data.Resource.Success
import lo.zaemtoperson.gola.domain.RepositoryAnalytic
import lo.zaemtoperson.gola.domain.RepositoryServer
import lo.zaemtoperson.gola.domain.Service
import lo.zaemtoperson.gola.domain.SharedKepper
import lo.zaemtoperson.gola.domain.model.StatusApplication

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: Service,
    private val sharedKeeper: SharedKepper,
    private val repositoryAnalytic: RepositoryAnalytic,
    private val repositoryServer: RepositoryServer
) : ViewModel() {
    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()


    fun loadData(instanceIdAppsFlyer: String?) {
        if (service.checkedInternetConnection()) {
            viewModelScope.launch {
                val appMetrika = service.appMetrika
                val instanceIdMyTracker = if (sharedKeeper.getMyTrackerInstanceId().isNullOrBlank()) {
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
                _state.value.copy(
                    appMetrica = appMetrika,
                    sim = sim,
                    instanceIdMyTracker = instanceIdMyTracker,
                    instanceIdAppsFlyer = instanceIdAppsFlyer,
                    isRoot = isRoot,
                    locale = locale,
                    deviceId = deviceId,
                    versionApplication = version,
                    isConnectInternet = true,
                    statusApplication = StatusApplication.Connect()
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
            viewModelScope.launch {
                service.getAppsFlyerDeeplink { deeplink ->
                    _state.value.copy(
                        appsFlyerDeeplink = deeplink
                    )
                        .updateStateUI()
                }
            }
            getRemoteConfig()
            getSub1()
            getFirstSub2()
            getSub3()
            getSub5()
        } else {
            _state.value.copy(
                isConnectInternet = false,
                statusApplication = StatusApplication.Mock(),
                message = "Need to connect internet"
            )
                .updateStateUI()
        }
    }

    private fun MainState.updateStateUI() {
        _state.update {
            this
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
                        isConnectInternet = false,
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

    private fun loadDbData() {
        viewModelScope.launch {
            delay(2000)
            when (val folder = repositoryServer.getFolder(
                sim = _state.value.sim ?: "",
                colorFb = _state.value.colorFb,
                deviceId = _state.value.deviceId ?: "",
                fbKey = _state.value.fireBaseToken ?: "",
                gaid = _state.value.gaid ?: "",
                instanceMyTracker = _state.value.instanceIdMyTracker ?: "",
                local = _state.value.locale,
                metrikaKey = _state.value.appMetrica,
                root = if (_state.value.isRoot) "granted" else "null",
                version = _state.value.versionApplication ?: ""
            )) {
                is Error -> {
                    _state.value.copy(
                        message = folder.message ?: "unknown error",
                        statusApplication = StatusApplication.Mock(),
                    )
                        .updateStateUI()
                }

                is Success -> {
                    if (folder.data?.folder.isNullOrBlank() || folder.data?.folder == "null") {
                        _state.value.copy(
                            statusApplication = StatusApplication.Mock(),
                        )
                            .updateStateUI()
                    } else {
                        when (val currentDate =
                            folder.data?.let { repositoryServer.getCurrentDate(it.folder) }) {
                            is Error -> {
                                _state.value.copy(
                                    message = currentDate.message ?: "unknown error",
                                    statusApplication = StatusApplication.Mock(),
                                )
                                    .updateStateUI()
                            }

                            is Success -> {
                                val newDate = currentDate.data?.date
                                val savedDate = sharedKeeper.getCurrentDate()
                                if (savedDate.isNullOrBlank() || savedDate != newDate) {
                                    sharedKeeper.setCurrentDate(newDate ?: "")
                                    when (val db = repositoryServer.getDataDb(folder.data.folder)) {
                                        is Error -> {
                                            _state.value.copy(
                                                statusApplication = StatusApplication.Mock(),
                                            )
                                                .updateStateUI()
                                        }

                                        is Success -> {
                                            _state.value.copy(
                                                statusApplication = StatusApplication.Connect(),
                                                dbData = db.data
                                            )
                                                .updateStateUI()
                                        }
                                    }
                                }
                            }

                            null -> {
                                _state.value.copy(
                                    statusApplication = StatusApplication.Mock(),
                                )
                                    .updateStateUI()
                            }
                        }
                    }
                }
            }
        }
    }
}