package com.bettigres.mi_1.ui.presentation

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bettigres.mi_1.R
import com.bettigres.mi_1.ui.theme.black
import com.bettigres.mi_1.ui.theme.gray
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarInput (
    modifier: Modifier = Modifier,
    placeHolder: String
) {
    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()
    val year = mCalendar.get(Calendar.YEAR)
    val month = mCalendar.get(Calendar.MONTH)
    val day = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    var calendarData by remember {
        mutableStateOf("")
    }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            calendarData = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, year, month, day
    )
    TextField(
        value = calendarData,
        onValueChange = { },
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            Text(
                color = gray,
                fontStyle = FontStyle(R.font.onest_400),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                text = placeHolder
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
                    .clickable { mDatePickerDialog.show() },
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_calendar_month_24),
                tint = black,
                contentDescription = "",
            )
        }
    )

}