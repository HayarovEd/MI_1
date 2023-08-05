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
import lo.zaemtoperson.gola.data.APPS_FLYER
import lo.zaemtoperson.gola.data.APP_METRICA
import lo.zaemtoperson.gola.data.APY_KEY
import lo.zaemtoperson.gola.data.Resource.Error
import lo.zaemtoperson.gola.data.Resource.Success
import lo.zaemtoperson.gola.domain.Repository
import lo.zaemtoperson.gola.domain.Service
import lo.zaemtoperson.gola.domain.SharedKepper

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: Service,
    private val sharedKeeper: SharedKepper,
    private val repository: Repository
) : ViewModel() {
    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        if (service.checkedInternetConnection()) {
            viewModelScope.launch {
                val appMetrika = service.appMetrika
                val instanceId = if (sharedKeeper.getMyTrackerInstanceId().isNullOrBlank()) {
                    val instance = service.instanceId
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
                    instanceId = instanceId,
                    isRoot = isRoot,
                    locale = locale,
                    deviceId = deviceId,
                    versionApplication = version,
                    isConnectInternet = true
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
        } else {
            _state.value.copy(
                isConnectInternet = false,
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
            val currentMyTrackerId = _state.value.instanceId
            when (val result = repository.getSub1(
                applicationToken = APY_KEY,
                userId = currentGaid?:"",
                appMetricaId = APP_METRICA,
                appsflyer = APPS_FLYER,
                firebaseToken = currentFireBaseToken?:"",
                myTrackerId = currentMyTrackerId?:""
            )) {
                is Error -> {
                    _state.value.copy(
                        message = result.message?: "unknown error"
                    )
                        .updateStateUI()
                }
                is Success -> {
                    _state.value.copy(
                        affsub1Unswer = result.data?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }
}