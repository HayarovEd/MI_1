package com.bettigres.mi_1.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bettigres.mi_1.R
import com.bettigres.mi_1.ui.theme.black
import com.bettigres.mi_1.ui.theme.green
import com.bettigres.mi_1.ui.theme.white
@Preview
@Composable
fun CardScreen(
    modifier: Modifier = Modifier,
    enabledButton: Boolean = true
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = green)
        .padding(24.dp)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Title()
            Spacer(modifier = modifier.height(47.dp))
            Text(
                text = stringResource(id = R.string.choice_card),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                color = black
            )
            Spacer(modifier = modifier.height(16.dp))
            Image(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { },
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.card_visa),
                contentDescription = "visa")
            //Spacer(modifier = modifier.height(16.dp))
            Image(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { },
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.card_maestro),
                contentDescription = "visa")
        }
        Button(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .align(alignment = Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = black,
                contentColor = white
            ),
            enabled = enabledButton,
            onClick = {  }) {
            Text(
                text = stringResource(id = R.string.checkout),
                fontSize = 18.sp,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold)
            )
        }
    }
}