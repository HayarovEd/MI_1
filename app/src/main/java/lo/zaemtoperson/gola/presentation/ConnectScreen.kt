package lo.zaemtoperson.gola.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        bottomBar = {
            BottomAppBar (
                containerColor = black
            )  {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
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
                            color = if (baseState is BaseState.Cards) green else lightGray,
                            content = stringResource(id = R.string.cards),
                            icon = ImageVector.vectorResource(id = R.drawable.ic_card),
                            onClick = onClickCards
                        )
                    }
                    if (db.credits.isNotEmpty()) {
                        ItemBottomBar(
                            color = if (baseState is BaseState.Credits) green else lightGray,
                            content = stringResource(id = R.string.credits),
                            icon = ImageVector.vectorResource(id = R.drawable.ic_credit),
                            onClick = onClickCredits
                        )
                    }
                }

            }
        }
    ) { valuePaddings ->
        Column (
            modifier = modifier
                .fillMaxSize()
                .background(color = baseBackground)
                .padding(valuePaddings),
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(db.loans) { loan ->
                    ItemLoan(
                        loan = loan,
                        onClickInfo = {},
                        onClickOffer = {})
                }
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
