package lo.zaemtoperson.gola.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
        val appMetrika = service.appMetrika
        val instanceId = service.instanceId
        val sim = service.getSimCountryIso()
        val isRoot = service.isRootedOne()
        val locale = service.getCurrentLocale()
        val deviceId = service.getDeviceAndroidId()
        service.getFirebaseMessagingToken {token->
            _state.value.copy(
                fireBaseToken = token
            )
                .updateStateUI()
        }
        val gaid = service.getGAID()
        val version = service.getApplicationVersion()
        service.getMyTrackerDeeplink {deeplink->
            _state.value.copy(
                trackerDeeplink = deeplink
            )
                .updateStateUI()
        }
        service.getAppsFlyerDeeplink {deeplink->
            _state.value.copy(
                appMetrica = appMetrika,
                sim = sim,
                instanceId = instanceId,
                isRoot = isRoot,
                locale = locale,
                deviceId = deviceId,
                gaid  =gaid,
                versionApplication = version,
                appsFlyerDeeplink = deeplink
            )
                .updateStateUI()
        }
    }

    private fun MainState.updateStateUI() {
        _state.update {
            this
        }
    }
}