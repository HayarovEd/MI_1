package com.bettigres.mi_1.ui.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun  BaseScreen (
) {
    val setScreen: MutableState<ScreenState> = remember {
        mutableStateOf(ScreenState.Card)
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
        ScreenState.Confirm -> TODO()
        ScreenState.DateTime -> {
            DateMeetScreen(
                setScreen = setScreen
            )
        }
        ScreenState.Selfie -> TODO()
    }
}