package hed.hotzaem.tophh.presentation

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import java.io.File
import java.util.concurrent.ExecutorService
import hed.hotzaem.tophh.domain.model.StatusApplication
import hed.hotzaem.tophh.domain.model.StatusApplication.Connect
import hed.hotzaem.tophh.domain.model.StatusApplication.Loading
import hed.hotzaem.tophh.domain.model.StatusApplication.Mock
import hed.hotzaem.tophh.domain.model.TypeCard
import hed.hotzaem.tophh.domain.model.basedto.BaseState
import hed.hotzaem.tophh.ui.presentation_v1.BaseScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Sample(
    viewModel: MainViewModel = hiltViewModel(),
    outputDirectory: File,
    executor: ExecutorService
) {

    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    val context = LocalContext.current

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
           Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            //Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    val loanLazyState = rememberLazyListState()
    val creditLazyState = rememberLazyListState()
    val creditCardloanLazyState = rememberLazyListState()
    val debitCardLazyState = rememberLazyListState()
    val instalmentCardLazyState = rememberLazyListState()
    val typeCard = if (!state.value.creditCards.isNullOrEmpty()) TypeCard.CardCredit
    else if (!state.value.debitCards.isNullOrEmpty()) TypeCard.CardDebit else TypeCard.CardInstallment
    when (val currentState = state.value.statusApplication) {
        is Connect -> {
            ConnectScreen(
                baseState = currentState.baseState,
                db = state.value.dbData!!,
                onClickCards = { onEvent(
                    MainEvent.OnChangeBaseState(
                        BaseState.Cards(
                    typeCard = typeCard
                ))
                ) },
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
                loanLazyState = loanLazyState,
                creditLazyState = creditLazyState,
                creditCardloanLazyState = creditCardloanLazyState,
                debitCardLazyState = debitCardLazyState,
                instalmentCardLazyState = instalmentCardLazyState,
                creditCards = state.value.creditCards,
                debitCards = state.value.debitCards,
                installmentCards = state.value.installmentCards,
                launcherMultiplePermissions = launcherMultiplePermissions,
                context = context,
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
            OfferScreen(
                elementOffer = (state.value.statusApplication as StatusApplication.Offer).elementOffer,
                baseState = (state.value.statusApplication as StatusApplication.Offer).currentBaseState,
                onEvent = viewModel::onEvent,
                launcherMultiplePermissions = launcherMultiplePermissions,
                context = context
            )
        }

        is StatusApplication.Web -> {
            WebViewScreen(
                url = currentState.url,
                offerName = currentState.offerName,
                onEvent = viewModel::onEvent,
            )
        }

        StatusApplication.NoConnect -> {
            NoInternetScreen(onEvent = viewModel::onEvent)
        }
    }

}