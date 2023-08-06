package lo.zaemtoperson.gola.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Sample (
    viewModel: MainViewModel = hiltViewModel(),
) {
    //viewModel.loadData(instanceIdAppsFlyer=instanceIdAppsFlyer)
    val state = viewModel.state.collectAsState()
    Log.d("AAAAAA", "db ${state.value.dbData}")
}