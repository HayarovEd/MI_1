package com.fast.zai.loans.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fast.zai.loans.R
import com.fast.zai.loans.ui.theme.absoluteDark
import com.fast.zai.loans.ui.theme.white

@Composable
fun Rang (
    modifier: Modifier = Modifier,
    rang: String
) {
    Row (
        modifier = modifier
            .clip(shape = RoundedCornerShape(CornerSize(100)))
            .background(color = absoluteDark)
            .width(40.dp)
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_star_9),
            tint = white,
            contentDescription = "")
        Spacer(modifier = modifier.width(4.dp))
        Text(
            color = white,
            fontStyle = FontStyle(R.font.onest_400),
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            text = rang
        )
    }
}