package lo.zaemtoperson.gola.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Sample (
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    println("appMetrica ${state.value.appMetrica}")
    println("sim ${state.value.sim}")
    println("instanceId ${state.value.instanceId}")
    println("isRoot ${state.value.isRoot}")
    println("locale ${state.value.locale}")
    println("deviceId ${state.value.deviceId}")
    println("fireBaseToken ${state.value.fireBaseToken}")
    println("versionApplication ${state.value.versionApplication}")
    println("trackerDeeplink ${state.value.trackerDeeplink}")
    println("appsFlyerDeeplink ${state.value.appsFlyerDeeplink}")
}