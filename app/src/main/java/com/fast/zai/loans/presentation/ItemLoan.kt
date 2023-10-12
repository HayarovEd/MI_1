package com.fast.zai.loans.presentation

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.fast.zai.loans.R
import com.fast.zai.loans.data.VALUE_ONE
import com.fast.zai.loans.domain.model.ElementOffer
import com.fast.zai.loans.domain.model.StatusApplication
import com.fast.zai.loans.domain.model.basedto.BaseState
import com.fast.zai.loans.domain.model.basedto.Loan
import com.fast.zai.loans.presentation.MainEvent.OnChangeStatusApplication
import com.fast.zai.loans.R.string
import com.fast.zai.loans.ui.theme.black
import com.fast.zai.loans.ui.theme.white

@Composable
fun ItemLoan(
    modifier: Modifier = Modifier,
    loan: Loan,
    onEvent: (MainEvent) -> Unit,
    baseState: BaseState,
    launcherMultiplePermissions: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    context: Context
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 4.dp, bottom = 4.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = white)
            .padding(16.dp)
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(
                        OnChangeStatusApplication(
                            StatusApplication.Offer(
                                currentBaseState = baseState,
                                ElementOffer(
                                    nameButton = loan.orderButtonText,
                                    name = loan.name,
                                    pathImage = loan.screen,
                                    rang = loan.score,
                                    description = loan.description,
                                    amount = loan.summPrefix + " " + loan.summMin + " " + loan.summMid + " " + loan.summMax + " " + loan.summPostfix,
                                    bet = loan.percentPrefix + " " + loan.percent + " " + loan.percentPostfix,
                                    term = loan.termPrefix + " " + loan.termMin + " " + loan.termMid + " " + loan.termMax + " " + loan.termPostfix,
                                    showMir = loan.showMir,
                                    showVisa = loan.showVisa,
                                    showMaster = loan.showMastercard,
                                    showQiwi = loan.showQiwi,
                                    showYandex = loan.showYandex,
                                    showCache = loan.showCash,
                                    showPercent = loan.hidePercentFields,
                                    showTerm = loan.hideTermFields,
                                    order = loan.order
                                )
                            )
                        )
                    )
                },
            model = loan.screen,
            contentScale = ContentScale.FillWidth,
            contentDescription = ""
        )
        Spacer(modifier = modifier.height(13.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                color = black,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                text = loan.name
            )
            Rang(
                rang = loan.score
            )
        }
        Spacer(modifier = modifier.height(13.dp))
        RowData(
            title = stringResource(id = string.amount),
            content = loan.summPrefix +" " + loan.summMin +" " + loan.summMid +" " + loan.summMax +" " + loan.summPostfix
        )
        if (loan.hidePercentFields == VALUE_ONE) {
            Spacer(modifier = modifier.height(8.dp))
            RowData(
                title = stringResource(id = string.bet),
                content = loan.percentPrefix +" " + loan.percent +" " + loan.percentPostfix
            )
        }
        if (loan.hideTermFields == VALUE_ONE) {
            Spacer(modifier = modifier.height(8.dp))
            RowData(
                title = stringResource(id = string.term),
                content = loan.termPrefix +" "+ loan.termMin +" " + loan.termMid +" " + loan.termMax +" " + loan.termPostfix
            )
        }
        Spacer(modifier = modifier.height(13.dp))
        RowCard(
            showVisa = loan.showVisa,
            showMaster = loan.showMastercard,
            showYandex = loan.showYandex,
            showMir = loan.showMir,
            showQivi = loan.showQiwi,
            showCache = loan.showCash
        )
        Spacer(modifier = modifier.height(13.dp))
        RowButtons(
            titleOffer = loan.orderButtonText,
            onEvent = onEvent,
            currentBaseState = baseState,
            name = loan.name,
            pathImage = loan.screen,
            rang = loan.score,
            description = loan.description,
            amount = loan.summPrefix +" " + loan.summMin +" " + loan.summMid +" " + loan.summMax +" " + loan.summPostfix,
            bet  = loan.percentPrefix +" " + loan.percent +" " + loan.percentPostfix,
            term = loan.termPrefix +" "+ loan.termMin +" " + loan.termMid +" " + loan.termMax +" " + loan.termPostfix,
            showMir = loan.showMir,
            showVisa = loan.showVisa,
            showMaster = loan.showMastercard,
            showQiwi = loan.showQiwi,
            showYandex = loan.showYandex,
            showCache = loan.showCash,
            showPercent = loan.hidePercentFields,
            showTerm = loan.hideTermFields,
            order = loan.order,
            launcherMultiplePermissions = launcherMultiplePermissions,
            context = context
        )
    }
}
