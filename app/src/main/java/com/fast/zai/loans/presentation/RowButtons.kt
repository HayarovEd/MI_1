package com.fast.zai.loans.presentation

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fast.zai.loans.R
import com.fast.zai.loans.domain.model.ElementOffer
import com.fast.zai.loans.domain.model.StatusApplication.Offer
import com.fast.zai.loans.domain.model.basedto.BaseState
import com.fast.zai.loans.presentation.MainEvent.OnChangeStatusApplication
import com.fast.zai.loans.presentation.MainEvent.OnGoToWeb
import com.fast.zai.loans.ui.theme.absoluteDark
import com.fast.zai.loans.ui.theme.baseBackground
import com.fast.zai.loans.ui.theme.black
import com.fast.zai.loans.ui.theme.white

@Composable
fun RowButtons(
    modifier: Modifier = Modifier,
    titleOffer: String,
    currentBaseState: BaseState,
    onEvent: (MainEvent) -> Unit,
    name: String,
    pathImage: String,
    rang: String,
    description: String,
    amount: String,
    bet: String,
    term: String,
    showMir: String,
    showVisa: String,
    showMaster: String,
    showQiwi: String,
    showYandex: String,
    showCache: String,
    showPercent: String,
    showTerm: String,
    order:String,
    launcherMultiplePermissions: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    context: Context
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .weight(1f)
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = baseBackground)
                .clickable(onClick = {
                    onEvent(
                        OnChangeStatusApplication(
                            Offer(
                                currentBaseState = currentBaseState,
                                ElementOffer(
                                    name = name,
                                    pathImage = pathImage,
                                    rang = rang,
                                    description = description,
                                    amount = amount,
                                    bet = bet,
                                    term = term,
                                    showMir = showMir,
                                    showVisa = showVisa,
                                    showMaster = showMaster,
                                    showQiwi = showQiwi,
                                    showYandex = showYandex,
                                    showCache = showCache,
                                    showPercent = showPercent,
                                    showTerm = showTerm,
                                    nameButton = titleOffer,
                                    order = order
                                )
                            )
                        )
                    )
                })
                .padding(vertical = 14.dp)
        ) {
            Icon(
                modifier = modifier.align(alignment = Alignment.Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.carbon_overflow),
                tint = absoluteDark,
                contentDescription = ""
            )
        }
        Spacer(modifier = modifier.width(9.dp))
        Box(
            modifier = modifier
                .weight(3f)
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = black)
                .clickable(onClick = {
                    onEvent(
                        OnGoToWeb(
                            urlOffer = order,
                            nameOffer = name
                        )
                    )
                })
                .padding(vertical = 16.dp)
        ) {
            Text(
                modifier = modifier.align(alignment = Alignment.Center),
                color = white,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                text = titleOffer,
                textAlign = TextAlign.Center
            )
        }
    }
}