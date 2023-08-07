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
import lo.zaemtoperson.gola.ui.presentation_v1.BaseScreen
import java.io.File
import java.util.concurrent.ExecutorService

@Composable
fun Sample(
    viewModel: MainViewModel = hiltViewModel(),
    outputDirectory: File,
    executor: ExecutorService,
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    when (val currentState = state.value.statusApplication) {
        is Connect -> {
            ConnectScreen(
                baseState = currentState.baseState,
                db = state.value.dbData!!,
                onClickCards = { onEvent(MainEvent.OnChangeBaseState(BaseState.Cards(
                    typeCard = TypeCard.CardCredit
                ))) },
                onClickCredits = { onEvent(MainEvent.OnChangeBaseState(BaseState.Credits)) },
                onClickLoans = { onEvent(MainEvent.OnChangeBaseState(BaseState.Loans)) },
                onClickRules = {
                    onEvent(
                        MainEvent.OnChangeStatusApplication(
                            StatusApplication.Info(
                                currentBaseState = currentState.baseState,
                                content = state.value.dbData!!.appConfig.privacyPolicyHtml
                            )
                        )
                    )
                },
                creditCards = state.value.creditCards,
                debitCards = state.value.debitCards,
                installmentCards = state.value.installmentCards,
                onEvent = viewModel::onEvent
            )
        }

        Loading -> {
            LoadingScreen()
        }

        is Mock -> {
            BaseScreen(
                outputDirectory = outputDirectory,
                executor = executor,
            )
        }

        is StatusApplication.Info -> {
            PrivacyScreen(
                content = state.value.dbData?.appConfig?.privacyPolicyHtml?:"",
                baseState = (state.value.statusApplication as StatusApplication.Info).currentBaseState,
                onEvent = viewModel::onEvent
            )
        }

        is StatusApplication.Offer -> {
            TODO()
        }

        is StatusApplication.Web -> {
            TODO()
        }

        StatusApplication.NoConnect -> {
            NoInternetScreen(onEvent = viewModel::onEvent)
        }
    }

}