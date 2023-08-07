package lo.zaemtoperson.gola.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.R.font
import lo.zaemtoperson.gola.domain.model.basedto.BaseDto
import lo.zaemtoperson.gola.domain.model.basedto.BaseState
import lo.zaemtoperson.gola.domain.model.basedto.BaseState.Cards
import lo.zaemtoperson.gola.domain.model.basedto.BaseState.Credits
import lo.zaemtoperson.gola.ui.theme.baseBackground
import lo.zaemtoperson.gola.ui.theme.black
import lo.zaemtoperson.gola.ui.theme.green
import lo.zaemtoperson.gola.ui.theme.lightGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    db: BaseDto,
    baseState: BaseState,
    onClickLoans: () -> Unit,
    onClickCards: () -> Unit,
    onClickCredits: () -> Unit,
    onClickOffer: () -> Unit,
    onClickRules: () -> Unit,
    onClickWeb: () -> Unit,
) {
    val title = when (baseState) {
        Cards -> stringResource(id = R.string.cards)
        Credits -> stringResource(id = R.string.credits)
        BaseState.Loans -> stringResource(id = R.string.loans)
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
                            fontStyle = FontStyle(font.soyuz_grotesk_bold),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            text = title
                        )
                        IconButton(onClick = onClickRules) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.info),
                                tint = black,
                                contentDescription = "")
                        }
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
                    if (db.loans.isNotEmpty()) {
                        ItemBottomBar(
                            color = if (baseState is BaseState.Loans) green else lightGray,
                            content = stringResource(id = R.string.loans),
                            icon = ImageVector.vectorResource(id = R.drawable.ic_bank),
                            onClick = onClickLoans
                        )
                    }
                    if (db.cards.isNotEmpty()) {
                        ItemBottomBar(
                            color = if (baseState is Cards) green else lightGray,
                            content = stringResource(id = R.string.cards),
                            icon = ImageVector.vectorResource(id = R.drawable.ic_card),
                            onClick = onClickCards
                        )
                    }
                    if (db.credits.isNotEmpty()) {
                        ItemBottomBar(
                            color = if (baseState is Credits) green else lightGray,
                            content = stringResource(id = R.string.credits),
                            icon = ImageVector.vectorResource(id = R.drawable.ic_credit),
                            onClick = onClickCredits
                        )
                    }
                }

            }
        }
    ) { valuePaddings ->
        when (baseState) {
            Cards -> {

            }

            Credits -> {
                Credits(
                    valuePaddings = valuePaddings,
                    credits = db.credits,
                    onClickWeb = onClickWeb,
                    onClickOffer = onClickOffer
                )
            }

            BaseState.Loans -> {
                Loans(
                    valuePaddings = valuePaddings,
                    loans = db.loans,
                    onClickWeb = onClickWeb,
                    onClickOffer = onClickOffer
                )
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
            fontStyle = FontStyle(font.onest_400),
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
