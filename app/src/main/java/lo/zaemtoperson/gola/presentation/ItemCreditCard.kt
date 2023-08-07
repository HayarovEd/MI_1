package lo.zaemtoperson.gola.presentation

import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.R.font
import lo.zaemtoperson.gola.data.VALUE_ONE
import lo.zaemtoperson.gola.domain.model.basedto.CardsCredit
import lo.zaemtoperson.gola.ui.theme.black
import lo.zaemtoperson.gola.ui.theme.white

@Composable
fun ItemCreditCard(
    modifier: Modifier = Modifier,
    card: CardsCredit,
    onClickWeb: () -> Unit,
    onClickOffer: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = white)
            .padding(16.dp)
    ) {
        AsyncImage(
            modifier = modifier.fillMaxWidth(),
            model = card.screen,
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
                fontStyle = FontStyle(font.soyuz_grotesk_bold),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                text = card.name
            )
            Rang(
                rang = card.score
            )
        }
        Spacer(modifier = modifier.height(13.dp))
        RowData(
            title = stringResource(id = R.string.amount),
            content = card.summPrefix +" " + card.summMin +" " + card.summMid +" " + card.summMax +" " + card.summPostfix
        )
        if (card.hidePercentFields == VALUE_ONE) {
            Spacer(modifier = modifier.height(8.dp))
            RowData(
                title = stringResource(id = R.string.bet),
                content = card.percentPrefix +" " + card.percent +" " + card.percentPostfix
            )
        }
        if (card.hideTermFields == VALUE_ONE) {
            Spacer(modifier = modifier.height(8.dp))
            RowData(
                title = stringResource(id = R.string.term),
                content = card.termPrefix +" "+ card.termMin +" " + card.termMid +" " + card.termMax +" " + card.termPostfix
            )
        }
        Spacer(modifier = modifier.height(13.dp))
        RowCard(
            showVisa = card.showVisa,
            showMaster = card.showMastercard,
            showYandex = card.showYandex,
            showMir = card.showMir,
            showQivi = card.showQiwi,
            showCache = card.showCash
        )
        Spacer(modifier = modifier.height(13.dp))
        RowButtons(
            titleOffer = card.orderButtonText,
            onClickWeb = onClickWeb,
            onClickOffer = onClickOffer
        )
    }
}
