package lo.zaemtoperson.gola.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lo.zaemtoperson.gola.R.font
import lo.zaemtoperson.gola.ui.theme.absoluteDark
import lo.zaemtoperson.gola.ui.theme.baseBackground
import lo.zaemtoperson.gola.ui.theme.white

@Composable
fun RowData(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = white)
            .border(width = 2.dp, color = baseBackground)
            .padding(horizontal = 8.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            color = absoluteDark,
            fontStyle = FontStyle(font.onest_400),
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            text = title,
            textAlign = TextAlign.Start
        )
        Text(
            color = absoluteDark,
            fontStyle = FontStyle(font.onest_700),
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            text = content,
            textAlign = TextAlign.End
        )
    }
}