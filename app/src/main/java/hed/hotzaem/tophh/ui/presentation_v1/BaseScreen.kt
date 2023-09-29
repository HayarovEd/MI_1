package hed.hotzaem.tophh.ui.presentation_v1

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.io.File
import java.util.concurrent.ExecutorService

@Composable
fun  BaseScreen(
    outputDirectory: File,
    executor: ExecutorService,
) {
    val setScreen: MutableState<ScreenState> = remember {
        mutableStateOf(ScreenState.Card)
    }
    val dateMeet: MutableState<String> = remember {
        mutableStateOf("")
    }
    val timeMeet: MutableState<String> = remember {
        mutableStateOf("")
    }
    when (setScreen.value) {
        ScreenState.BaseData -> {
            UserDataScreen(
                setScreen = setScreen
            )
        }
        ScreenState.Card -> {
            CardScreen(
                setScreen = setScreen
            )
        }
        ScreenState.Confirm -> {
            ConfirmScreen(
                dateMeet = dateMeet.value,
                timeMeet = timeMeet.value
            )
        }
        ScreenState.DateTime -> {
            DateMeetScreen(
                setScreen = setScreen,
                dateMeet = dateMeet,
                timeMeet = timeMeet
            )
        }
        ScreenState.Selfie -> {
            SelfieScreen(
                setScreen = setScreen,
                outputDirectory = outputDirectory,
                executor = executor,
            )
        }
    }
}