package lo.zaemtoperson.gola.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import lo.zaemtoperson.gola.domain.model.StatusApplication
import lo.zaemtoperson.gola.domain.model.StatusApplication.Connect
import lo.zaemtoperson.gola.domain.model.StatusApplication.Loading
import lo.zaemtoperson.gola.domain.model.StatusApplication.Mock
import lo.zaemtoperson.gola.domain.model.TypeCard
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
                onClickCards = { onEvent(MainEvent.OnChangeBaseState(BaseState.Cards(TypeCard.CardCredit))) },
                onClickCredits = { onEvent(MainEvent.OnChangeBaseState(BaseState.Credits)) },
                onClickLoans = { onEvent(MainEvent.OnChangeBaseState(BaseState.Loans)) },
                onClickOffer = {},
                onClickRules = {},
                onClickWeb = {},
                creditCards = state.value.creditCards,
                debitCards = state.value.debitCards,
                installmentCards = state.value.installmentCards,
                onEvent = viewModel::onEvent
            )
        }

        Loading -> {

        }

        Mock -> {

        }

        is StatusApplication.Info -> TODO()
        is StatusApplication.Offer -> TODO()
        is StatusApplication.Web -> TODO()
    }

}