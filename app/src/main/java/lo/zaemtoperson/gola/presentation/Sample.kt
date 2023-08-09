package lo.zaemtoperson.gola.presentation

import android.os.Build
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Sample(
    viewModel: MainViewModel = hiltViewModel(),
    outputDirectory: File,
    executor: ExecutorService,
    webView: WebView,
) {
    val state = viewModel.state.collectAsState()
    val sd = viewModel.notificationLiveData
    Log.d("BBBBBB", "sd ${sd.value?.message}")
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
                webView = webView,
                url = currentState.url
            )
        }

        StatusApplication.NoConnect -> {
            NoInternetScreen(onEvent = viewModel::onEvent)
        }
    }

}