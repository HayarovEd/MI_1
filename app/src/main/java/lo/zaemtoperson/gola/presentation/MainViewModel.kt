package lo.zaemtoperson.gola.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lo.zaemtoperson.gola.domain.Service
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val service: Service): ViewModel() {
    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val appMetrika = service.appMetrika
            val instanceId = service.instanceId
            val sim = service.getSimCountryIso()
            val isRoot = service.isRootedOne()
            val locale = service.getCurrentLocale()
            val deviceId = service.getDeviceAndroidId()
            service.getFirebaseMessagingToken { token ->
                _state.value.copy(
                    fireBaseToken = token
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
            )
                .updateStateUI()
        }
        viewModelScope.launch (Dispatchers.IO) {
            val gaid = service.getGAID()
            _state.value.copy(
                gaid = gaid,
            )
                .updateStateUI()
        }
        viewModelScope.launch {
            service.getMyTrackerDeeplink { deeplink->
                _state.value.copy(
                    trackerDeeplink = deeplink
                )
                    .updateStateUI()
            }
        }
        viewModelScope.launch {
            service.getAppsFlyerDeeplink { deeplink->
                _state.value.copy(
                    appsFlyerDeeplink = deeplink
                )
                    .updateStateUI()
            }
        }
    }

    private fun MainState.updateStateUI() {
        _state.update {
            this
        }
    }
}