package com.bettigres.mi_1.ui.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bettigres.mi_1.R
import com.bettigres.mi_1.ui.theme.black

@Composable
fun Title(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = black
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_undo_24),
                contentDescription = "back"
            )
        }
        Spacer(modifier = modifier.width(16.dp))
        Text(
            text = stringResource(id = R.string.formalization),
            fontSize = 18.sp,
            fontWeight = Bold,
            fontStyle = FontStyle(R.font.onest_700),
            color = black
        )
    }
}