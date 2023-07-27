package com.bettigres.mi_1.ui.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bettigres.mi_1.R
import com.bettigres.mi_1.ui.theme.black
import com.bettigres.mi_1.ui.theme.gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    content: String,
    onSetContent: (String) -> Unit,
    placeHolder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp)),
        value = content,
        onValueChange = onSetContent,
        textStyle = LocalTextStyle.current.copy(
            color = black,
            fontStyle = FontStyle(R.font.onest_400),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        ),
        placeholder = {
            Text(
                text = placeHolder,
                color = gray,
                fontStyle = FontStyle(R.font.onest_400),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )
        },
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType=keyboardType),
    )
}