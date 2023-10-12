package com.fast.zai.loans.ui.presentation_v1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fast.zai.loans.R
import com.fast.zai.loans.ui.theme.black
import com.fast.zai.loans.ui.theme.gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    content: String,
    onSetContent: (String) -> Unit,
    placeHolder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction:ImeAction = ImeAction.Default,
    onClickIcon: () -> Unit = {},
    readOnly: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp)),
        value = content,
        onValueChange = onSetContent,
        enabled = enabled,
        readOnly = readOnly,
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
                modifier = Modifier.clickable { onClickIcon.invoke() },
                imageVector = icon,
                contentDescription = ""
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType=keyboardType,
            imeAction = imeAction),
        keyboardActions = keyboardActions,
    )
}