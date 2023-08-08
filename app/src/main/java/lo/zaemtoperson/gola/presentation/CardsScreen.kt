package lo.zaemtoperson.gola.presentation

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.domain.model.TypeCard
import lo.zaemtoperson.gola.domain.model.basedto.BaseState
import lo.zaemtoperson.gola.domain.model.basedto.CardsCredit
import lo.zaemtoperson.gola.domain.model.basedto.CardsDebit
import lo.zaemtoperson.gola.domain.model.basedto.CardsInstallment
import lo.zaemtoperson.gola.ui.theme.absoluteDark
import lo.zaemtoperson.gola.ui.theme.baseBackground
import lo.zaemtoperson.gola.ui.theme.green

@Composable
fun CardsScreen(
    modifier: Modifier = Modifier,
    valuePaddings: PaddingValues,
    creditCards: List<CardsCredit>,
    debitCards: List<CardsDebit>,
    installmentCards: List<CardsInstallment>,
    typeCard: TypeCard,
    onEvent: (MainEvent) -> Unit,
    baseState: BaseState,
    launcherMultiplePermissions: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    context: Context
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = baseBackground)
            .padding(valuePaddings),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (creditCards.isNotEmpty()) {
                Button(
                    modifier = modifier.weight(1f),
                    onClick = { onEvent(MainEvent.OnChangeTypeCard(TypeCard.CardCredit)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (typeCard is TypeCard.CardCredit) green else baseBackground
                    )
                ) {
                    Text(
                        color = absoluteDark,
                        fontStyle = FontStyle(R.font.onast_500),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        text = stringResource(id = R.string.credit),
                        textAlign = TextAlign.Center
                    )
                }
            }
            if (debitCards.isNotEmpty()) {
                Button(
                    modifier = modifier.weight(1f),
                    onClick = { onEvent(MainEvent.OnChangeTypeCard(TypeCard.CardDebit)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (typeCard is TypeCard.CardDebit) green else baseBackground
                    )
                ) {
                    Text(
                        color = absoluteDark,
                        fontStyle = FontStyle(R.font.onast_500),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        text = stringResource(id = R.string.debit),
                        textAlign = TextAlign.Center
                    )
                }
            }
            if (installmentCards.isNotEmpty()) {
                Button(
                    modifier = modifier.weight(1f),
                    onClick = { onEvent(MainEvent.OnChangeTypeCard(TypeCard.CardInstallment)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (typeCard is TypeCard.CardInstallment) green else baseBackground
                    )
                ) {
                    Text(
                        color = absoluteDark,
                        fontStyle = FontStyle(R.font.onast_500),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        text = stringResource(id = R.string.installment),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(24.dp))
        when (typeCard) {
            TypeCard.CardCredit -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(creditCards) { card ->
                        ItemCreditCard(
                            card = card,
                            onEvent = onEvent,
                            baseState = baseState,
                            launcherMultiplePermissions = launcherMultiplePermissions,
                            context = context
                        )
                    }
                }
            }

            TypeCard.CardDebit -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(debitCards) { card ->
                        ItemDebitCard(
                            card = card,
                            onEvent = onEvent,
                            baseState = baseState,
                            launcherMultiplePermissions = launcherMultiplePermissions,
                            context = context
                        )
                    }
                }
            }

            TypeCard.CardInstallment -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(installmentCards) { card ->
                        ItemInstallmentCard(
                            card = card,
                            baseState = baseState,
                            onEvent = onEvent,
                            launcherMultiplePermissions = launcherMultiplePermissions,
                            context = context
                        )
                    }
                }
            }
        }

    }
}