package hed.hotzaem.tophh.ui.presentation_v1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hed.hotzaem.tophh.R
import hed.hotzaem.tophh.gola.ui.theme.black
import hed.hotzaem.tophh.gola.ui.theme.green
import hed.hotzaem.tophh.gola.ui.theme.white

@Composable
fun DialogAccess (
    modifier: Modifier = Modifier,
    question: String,
    onYesClick: () -> Unit,
    onNoClick: () -> Unit,
){
    Column (
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = black)
            .padding(top = 58.dp, bottom = 41.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            modifier = modifier.size(64.dp),
            painter = painterResource(id = R.drawable.access),
            contentDescription = ""
        )
        Spacer(modifier = modifier.height(20.dp))
        Text(
            text = question,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
            color = white
        )
        Spacer(modifier = modifier.height(58.dp))
        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = modifier
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                onClick = onYesClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = green,
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.yes),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                    color = black
                )
            }
            Spacer(modifier = modifier.width(9.dp))
            Button(
                modifier = modifier
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                onClick = onNoClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = white,
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.no),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                    color = black
                )
            }
        }
    }
}