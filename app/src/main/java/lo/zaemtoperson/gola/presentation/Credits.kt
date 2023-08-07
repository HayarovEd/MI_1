package lo.zaemtoperson.gola.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lo.zaemtoperson.gola.domain.model.basedto.Credit
import lo.zaemtoperson.gola.domain.model.basedto.Loan
import lo.zaemtoperson.gola.ui.theme.baseBackground

@Composable
fun Credits(
    modifier: Modifier = Modifier,
    valuePaddings: PaddingValues,
    credits: List<Credit>,
    onClickOffer: () -> Unit,
    onClickWeb: () -> Unit,
) {
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(credits) { credit ->
                ItemCredit(
                    credit = credit,
                    onClickWeb = onClickWeb,
                    onClickOffer = onClickOffer
                )
            }
        }
    }
}