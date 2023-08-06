package lo.zaemtoperson.gola.presentation

import androidx.compose.foundation.background
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
import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.R.font
import lo.zaemtoperson.gola.ui.theme.absoluteDark
import lo.zaemtoperson.gola.ui.theme.green
import lo.zaemtoperson.gola.ui.theme.white

@Composable
fun Rang (
    modifier: Modifier = Modifier,
    rang: String
) {
    Row (
        modifier = modifier
            .clip(shape = RoundedCornerShape(CornerSize(100)))
            .background(color = absoluteDark)
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_star_9),
            tint = green,
            contentDescription = "")
        Spacer(modifier = modifier.width(4.dp))
        Text(
            color = white,
            fontStyle = FontStyle(font.onest_400),
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            text = rang
        )
    }
}