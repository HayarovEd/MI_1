package lo.zaemtoperson.gola.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Sample (
    viewModel: MainViewModel = hiltViewModel(),
    instanceIdAppsFlyer: String?
) {
    viewModel.loadData(instanceIdAppsFlyer=instanceIdAppsFlyer)
    val state = viewModel.state.collectAsState()
    /*println("AAAA appMetrica ${state.value.appMetrica}")
    println("AAAA sim ${state.value.sim}")
    println("AAAA instanceId ${state.value.instanceId}")
    println("AAAA colorFb ${state.value.colorFb}")
    println("AAAA isRoot ${state.value.isRoot}")
    println("AAAA locale ${state.value.locale}")
    println("AAAA gaid ${state.value.gaid}")
    println("AAAA deviceId ${state.value.deviceId}")
    println("AAAA fireBaseToken ${state.value.fireBaseToken}")
    println("AAAA versionApplication ${state.value.versionApplication}")
    println("AAAA trackerDeeplink ${state.value.trackerDeeplink}")
    println("AAAA appsFlyerDeeplink ${state.value.appsFlyerDeeplink}")*/

    println("AAAA sub1 ${state.value.affsub3Unswer}")
    println("AAAA message ${state.value.message}")
}