package hed.hotzaem.tophh.gola.ui.presentation_v1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lo.zaemtoperson.gola.R
import hed.hotzaem.tophh.gola.ui.theme.black
import hed.hotzaem.tophh.gola.ui.theme.green
import hed.hotzaem.tophh.gola.ui.theme.white

@Composable
fun ConfirmScreen (
    modifier: Modifier = Modifier,
    dateMeet: String,
    timeMeet: String,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = black)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.baseline_check_circle_100
                ),
                contentDescription = ""
            )
            Spacer(modifier = modifier.height(33.dp))
            Text(
                text = stringResource(id = R.string.ok),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                color = green
            )
            Spacer(modifier = modifier.height(33.dp))
            Text(
                text = "${stringResource(id = R.string.meet)} $dateMeet\n в $timeMeet",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle(R.font.onast_500),
                color = white
            )
        }
    }
}