package lo.zaemtoperson.gola.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import lo.zaemtoperson.gola.domain.model.basedto.BaseState
import lo.zaemtoperson.gola.domain.model.basedto.Loan
import lo.zaemtoperson.gola.ui.theme.baseBackground

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Loans(
    modifier: Modifier = Modifier,
    valuePaddings: PaddingValues,
    loans: List<Loan>,
    onEvent: (MainEvent) -> Unit,
    baseState: BaseState,
    launcherMultiplePermissions: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    context: Context,
) {
    val listState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = baseBackground)
            .padding(valuePaddings),
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = listState
        ) {
            items(loans) { loan ->
                ItemLoan(
                    loan = loan,
                    onEvent = onEvent,
                    baseState = baseState,
                    launcherMultiplePermissions = launcherMultiplePermissions,
                    context = context
                )
            }
        }
    }
}