package com.fast.zai.loans.presentation

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fast.zai.loans.R
import com.fast.zai.loans.R.drawable
import com.fast.zai.loans.R.string
import com.fast.zai.loans.data.VALUE_ONE
import com.fast.zai.loans.domain.model.basedto.BaseDto
import com.fast.zai.loans.domain.model.basedto.BaseState
import com.fast.zai.loans.domain.model.basedto.BaseState.Cards
import com.fast.zai.loans.domain.model.basedto.BaseState.Credits
import com.fast.zai.loans.domain.model.basedto.BaseState.Loans
import com.fast.zai.loans.domain.model.basedto.BaseState.WebPrimary
import com.fast.zai.loans.domain.model.basedto.CardsCredit
import com.fast.zai.loans.domain.model.basedto.CardsDebit
import com.fast.zai.loans.domain.model.basedto.CardsInstallment
import com.fast.zai.loans.ui.theme.baseBackground
import com.fast.zai.loans.ui.theme.black
import com.fast.zai.loans.ui.theme.green
import com.fast.zai.loans.ui.theme.lightGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    db: BaseDto,
    baseState: BaseState,
    creditCards: List<CardsCredit>,
    debitCards: List<CardsDebit>,
    installmentCards: List<CardsInstallment>,
    launcherMultiplePermissions: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    context: Context,
    onEvent: (MainEvent) -> Unit,
    onClickPrimary: () -> Unit,
    onClickLoans: () -> Unit,
    onClickCards: () -> Unit,
    onClickCredits: () -> Unit,
    onClickRules: () -> Unit,
    loanLazyState: LazyListState,
    creditLazyState: LazyListState,
    creditCardloanLazyState: LazyListState,
    debitCardLazyState: LazyListState,
    instalmentCardLazyState: LazyListState,
) {
    val title = when (baseState) {
        is Cards -> stringResource(id = string.cards)
        Credits -> stringResource(id = string.credits)
        Loans -> stringResource(id = string.loans)
        is WebPrimary -> db.appConfig.namePrimary?: ""
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            color = black,
                            fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            text = title
                        )
                        /*IconButton(onClick = onClickRules) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.info),
                                tint = black,
                                contentDescription = "")
                        }*/
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = baseBackground
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = black
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (!db.appConfig.showedIconPrimary.isNullOrEmpty()
                        && db.appConfig.showedIconPrimary == VALUE_ONE
                        && !db.appConfig.namePrimary.isNullOrEmpty()
                        && !db.appConfig.urlPrimary.isNullOrEmpty()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(
                                onClick = onClickPrimary) {
                                Image(
                                    imageVector = ImageVector.vectorResource(drawable.zaymer_home_page),
                                    contentDescription = "")
                            }
                            Text(
                                color = if (baseState is Loans) green else lightGray,
                                fontStyle = FontStyle(R.font.onest_400),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                text = db.appConfig.namePrimary
                            )
                        }
                    }
                    if (!db.loans.isNullOrEmpty()) {
                        ItemBottomBar(
                            color = if (baseState is Loans) green else lightGray,
                            content = stringResource(id = string.loans),
                            icon = ImageVector.vectorResource(id = drawable.ic_bank),
                            onClick = onClickLoans
                        )
                    }
                    if (!db.cards.isNullOrEmpty()) {
                        ItemBottomBar(
                            color = if (baseState is Cards) green else lightGray,
                            content = stringResource(id = string.cards),
                            icon = ImageVector.vectorResource(id = drawable.ic_card),
                            onClick = onClickCards
                        )
                    }
                    if (!db.credits.isNullOrEmpty()) {
                        ItemBottomBar(
                            color = if (baseState is Credits) green else lightGray,
                            content = stringResource(id = string.credits),
                            icon = ImageVector.vectorResource(id = drawable.ic_credit),
                            onClick = onClickCredits
                        )
                    }
                }

            }
        }
    ) { valuePaddings ->
        when (val type = baseState) {
            is Cards -> {
                CardsScreen(
                    valuePaddings = valuePaddings,
                    creditCards = creditCards,
                    debitCards = debitCards,
                    installmentCards = installmentCards,
                    typeCard = type.typeCard,
                    onEvent = onEvent,
                    baseState = baseState,
                    launcherMultiplePermissions = launcherMultiplePermissions,
                    context = context,
                    creditCardloanLazyState = creditCardloanLazyState,
                    debitCardLazyState = debitCardLazyState,
                    instalmentCardLazyState = instalmentCardLazyState,
                )
            }

            Credits -> {
                Credits(
                    valuePaddings = valuePaddings,
                    credits = db.credits,
                    onEvent = onEvent,
                    baseState = baseState,
                    launcherMultiplePermissions = launcherMultiplePermissions,
                    context = context,
                    creditLazyState = creditLazyState
                )
            }

            Loans -> {
                Loans(
                    valuePaddings = valuePaddings,
                    loans = db.loans,
                    onEvent = onEvent,
                    baseState = baseState,
                    launcherMultiplePermissions = launcherMultiplePermissions,
                    context = context,
                    loanLazyState = loanLazyState
                )
            }

            is WebPrimary -> {
                WebViewScreenPrimary(
                    url = db.appConfig.urlPrimary?:"",
                    offerName = db.appConfig.namePrimary?:"",
                    valuePaddings = valuePaddings,
                    onEvent = onEvent)
            }
        }
    }
}

@Composable
fun ItemBottomBar(
    color: Color,
    icon: ImageVector,
    content: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = color
            ),
            onClick = onClick) {
            Icon(imageVector = icon, contentDescription = "")
        }
        Text(
            color = color,
            fontStyle = FontStyle(R.font.onest_400),
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            text = content
        )
    }
}
/*
AndroidView(
modifier = modifier.padding(it),
factory = { context -> TextView(context) },
update = { it.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT) }
)*/
