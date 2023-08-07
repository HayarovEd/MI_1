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
import lo.zaemtoperson.gola.domain.model.basedto.Credit
import lo.zaemtoperson.gola.ui.theme.black
import lo.zaemtoperson.gola.ui.theme.white

@Composable
fun ItemCredit(
    modifier: Modifier = Modifier,
    credit: Credit,
    onClickOffer: () -> Unit,
    onClickWeb: () -> Unit,
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
            model = credit.screen,
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
                text = credit.name
            )
            Rang(
                rang = credit.score
            )
        }
        Spacer(modifier = modifier.height(13.dp))
        RowData(
            title = stringResource(id = R.string.amount),
            content = credit.summPrefix +" " + credit.summMin +" " + credit.summMid +" " + credit.summMax +" " + credit.summPostfix
        )
        if (credit.hidePercentFields == VALUE_ONE) {
            Spacer(modifier = modifier.height(8.dp))
            RowData(
                title = stringResource(id = R.string.bet),
                content = credit.percentPrefix +" " + credit.percent +" " + credit.percentPostfix
            )
        }
        if (credit.hideTermFields == VALUE_ONE) {
            Spacer(modifier = modifier.height(8.dp))
            RowData(
                title = stringResource(id = R.string.term),
                content = credit.termPrefix +" "+ credit.termMin +" " + credit.termMid +" " + credit.termMax +" " + credit.termPostfix
            )
        }
        Spacer(modifier = modifier.height(13.dp))
        RowCard(
            showVisa = credit.showVisa,
            showMaster = credit.showMastercard,
            showYandex = credit.showYandex,
            showMir = credit.showMir,
            showQivi = credit.showQiwi,
            showCache = credit.showCash
        )
        Spacer(modifier = modifier.height(13.dp))
        RowButtons(
            titleOffer = credit.orderButtonText,
            onClickWeb = onClickWeb,
            onClickOffer = onClickOffer
        )
    }
}
