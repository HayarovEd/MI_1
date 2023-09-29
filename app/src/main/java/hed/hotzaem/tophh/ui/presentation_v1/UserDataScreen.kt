package hed.hotzaem.tophh.ui.presentation_v1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hed.hotzaem.tophh.R
import hed.hotzaem.tophh.gola.ui.theme.black
import hed.hotzaem.tophh.gola.ui.theme.gray
import hed.hotzaem.tophh.gola.ui.theme.green
import hed.hotzaem.tophh.gola.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataScreen(
    modifier: Modifier = Modifier,
    setScreen: MutableState<ScreenState>,
) {
    val listItems = arrayOf(
        stringResource(id = R.string.male),
        stringResource(id = R.string.female),
    )
    val name: MutableState<String> = remember {
        mutableStateOf("")
    }
    val city: MutableState<String> = remember {
        mutableStateOf("")
    }
    val birthday: MutableState<String> = remember {
        mutableStateOf("")
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    var gender by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = green)
            .padding(24.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Title(
                onClick = {
                    setScreen.value = ScreenState.Card
                }
            )
            Spacer(modifier = modifier.height(47.dp))
            Text(
                text = stringResource(id = R.string.fill_fields),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                color = black
            )
            Spacer(modifier = modifier.height(16.dp))
            StandardTextField(
                content = name.value,
                onSetContent = { name.value = it },
                placeHolder = stringResource(id = R.string.fio),
                icon = ImageVector.vectorResource(id = R.drawable.baseline_person_outline_24)
            )
            Spacer(modifier = modifier.height(7.dp))
            StandardTextField(
                content = city.value,
                onSetContent = { city.value = it },
                placeHolder = stringResource(id = R.string.city),
                icon = ImageVector.vectorResource(id = R.drawable.baseline_business_24)
            )
            Spacer(modifier = modifier.height(7.dp))
            CalendarInput(
                placeHolder = stringResource(id = R.string.birthday),
                content = birthday,
                enabled = false,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            Spacer(modifier = modifier.height(7.dp))
            TextField(
                value = gender,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = true,
                enabled = false,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                placeholder = {
                    Text(
                        color = gray,
                        fontStyle = FontStyle(R.font.onest_400),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        text = stringResource(id = R.string.gender)
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    color = black,
                    fontStyle = FontStyle(R.font.onest_400),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                ),
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .rotate(if (expanded) 180f else 0f)
                            .clickable { expanded = !expanded },
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_drop_down_24),
                        tint = black,
                        contentDescription = "",
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                listItems.forEach { label ->
                    DropdownMenuItem(
                        modifier =Modifier.height(100.dp),
                        text = {
                            Text(
                                text = label,
                                color = black,
                                fontStyle = FontStyle(R.font.onest_400),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }, onClick = {
                            gender = label
                            expanded = false
                        })
                }
            }
        }
        Button(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .align(alignment = Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = black,
                contentColor = white
            ),
            enabled = name.value != ""
                    && city.value != ""
                    && birthday.value != ""
                    && gender != "",
            onClick = {
                setScreen.value = ScreenState.DateTime
            }
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    fontSize = 18.sp,
                    color = white,
                    fontStyle = FontStyle(R.font.soyuz_grotesk_bold)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic24_redo),
                    tint = white,
                    contentDescription = "back"
                )
            }
        }
    }
}