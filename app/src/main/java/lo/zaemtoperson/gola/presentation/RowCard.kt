package lo.zaemtoperson.gola.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.data.VALUE_ONE

@Composable
fun RowCard(
    modifier: Modifier = Modifier,
    showVisa: String,
    showMaster: String,
    showYandex: String,
    showMir: String,
    showQivi: String,
    showCache: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(showVisa==VALUE_ONE) {
            Image(
                modifier = modifier.weight(1f),
                imageVector = ImageVector.vectorResource(id = R.drawable.visa),
                contentDescription = "")
        }
        if(showMaster==VALUE_ONE) {
            Image(
                modifier = modifier.weight(1f),
                imageVector = ImageVector.vectorResource(id = R.drawable.master),
                contentDescription = "")
        }
        if(showYandex==VALUE_ONE) {
            Image(
                modifier = modifier.weight(1f),
                imageVector = ImageVector.vectorResource(id = R.drawable.y_money),
                contentDescription = "")
        }
        if(showMir==VALUE_ONE) {
            Image(
                modifier = modifier.weight(1f),
                imageVector = ImageVector.vectorResource(id = R.drawable.mir),
                contentDescription = "")
        }
        if(showQivi==VALUE_ONE) {
            Image(
                modifier = modifier.weight(1f),
                imageVector = ImageVector.vectorResource(id = R.drawable.qivi),
                contentDescription = "")
        }
        if(showCache==VALUE_ONE) {
            Image(
                modifier = modifier.weight(1f),
                imageVector = ImageVector.vectorResource(id = R.drawable.money),
                contentDescription = "")
        }
    }
}