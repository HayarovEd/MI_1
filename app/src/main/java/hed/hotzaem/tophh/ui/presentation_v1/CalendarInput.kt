package hed.hotzaem.tophh.ui.presentation_v1

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import hed.hotzaem.tophh.R
import hed.hotzaem.tophh.gola.ui.theme.black
import hed.hotzaem.tophh.gola.ui.theme.gray
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarInput (
    modifier: Modifier = Modifier,
    placeHolder: String,
    content: MutableState<String>,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true
) {
    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()
    val year = mCalendar.get(Calendar.YEAR)
    val month = mCalendar.get(Calendar.MONTH)
    val day = mCalendar.get(Calendar.DAY_OF_MONTH)


    mCalendar.time = Date()


    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            content.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, year, month, day
    )
    TextField(
        value = content.value,
        onValueChange = { },
        readOnly = true,
        enabled = enabled,
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
        },
        keyboardActions = keyboardActions,
    )

}