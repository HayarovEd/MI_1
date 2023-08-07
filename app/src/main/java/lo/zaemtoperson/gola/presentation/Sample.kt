package lo.zaemtoperson.gola.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import lo.zaemtoperson.gola.domain.model.StatusApplication.Connect
import lo.zaemtoperson.gola.domain.model.StatusApplication.Loading
import lo.zaemtoperson.gola.domain.model.StatusApplication.Mock
import lo.zaemtoperson.gola.domain.model.basedto.BaseState

@Composable
fun Sample (
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    when (val currentState = state.value.statusApplication) {
        is Connect -> {
            ConnectScreen(
                baseState = currentState.baseState,
                db = state.value.dbData!!,
                onClickCards = { onEvent(MainEvent.onChangeBaseState(BaseState.Cards)) },
                onClickCredits = { onEvent(MainEvent.onChangeBaseState(BaseState.Credits)) },
                onClickLoans = { onEvent(MainEvent.onChangeBaseState(BaseState.Loans)) },
                onClickInfo = {},
                onClickOffer = {},
                onClickRules = {}
            )
        }

        Loading -> {

        }

        Mock -> {

        }
    }

}