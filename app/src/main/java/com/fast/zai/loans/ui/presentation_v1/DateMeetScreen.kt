package com.fast.zai.loans.ui.presentation_v1

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fast.zai.loans.ui.presentation_v1.ScreenState.BaseData
import com.fast.zai.loans.ui.presentation_v1.ScreenState.Selfie
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.fast.zai.loans.R
import com.fast.zai.loans.R.drawable
import com.fast.zai.loans.R.string
import com.fast.zai.loans.ui.theme.black
import com.fast.zai.loans.ui.theme.green
import com.fast.zai.loans.ui.theme.white


@SuppressLint("NewApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DateMeetScreen(
    modifier: Modifier = Modifier,
    setScreen: MutableState<ScreenState>,
    dateMeet: MutableState<String>,
    timeMeet: MutableState<String>,
) {
    val phone: MutableState<String> = remember {
        mutableStateOf("")
    }

    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }

    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }
    val timeDialogState = rememberMaterialDialogState()
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = green)
            .padding(24.dp)
    ) {
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "Ok") {
                    timeMeet.value = formattedTime
                }
                negativeButton(text = "Cancel")
            }
        ) {
            timepicker(
                initialTime = LocalTime.NOON,
                title = "Pick a time",
                timeRange = LocalTime.MIDNIGHT..LocalTime.NOON
            ) {
                pickedTime = it
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Title(
                onClick = {
                    setScreen.value = BaseData
                }
            )
            Spacer(modifier = modifier.height(47.dp))
            Text(
                text = stringResource(id = string.choice_time),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                color = black
            )
            Spacer(modifier = modifier.height(16.dp))
            CalendarInput(
                placeHolder = stringResource(id = string.date),
                content = dateMeet,
                enabled = false,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            Spacer(modifier = modifier.height(7.dp))
            StandardTextField(
                content = timeMeet.value,
                onSetContent = { timeMeet.value = it },
                placeHolder = stringResource(id = string.time),
                keyboardType = KeyboardType.Decimal,
                icon = ImageVector.vectorResource(id = drawable.baseline_access_alarm_24),
                onClickIcon = { timeDialogState.show() },
                readOnly = true,
                enabled = false,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            Spacer(modifier = modifier.height(7.dp))
            StandardTextField(
                content = phone.value,
                onSetContent = { phone.value = it },
                placeHolder = stringResource(id = string.phone),
                icon = ImageVector.vectorResource(id = drawable.ic24_phone_talk),
                keyboardType = KeyboardType.Phone
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = stringResource(id = string.check_phone),
                fontSize = 16.sp,
                color = black,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle(R.font.onast_500)
            )
        }
        Column (
            modifier = modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                text = stringResource(id = string.privacy_policy),
                fontSize = 16.sp,
                color = black,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle(R.font.onast_500)
            )
            Spacer(modifier = modifier.height(32.dp))
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = black,
                    contentColor = white
                ),
                enabled = dateMeet.value != ""
                        && timeMeet.value != ""
                        && phone.value != "",
                onClick = {
                    setScreen.value = Selfie
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
                        text = stringResource(id = string.next),
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
}