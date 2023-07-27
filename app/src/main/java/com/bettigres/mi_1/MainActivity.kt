package com.bettigres.mi_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bettigres.mi_1.ui.presentation.BaseScreen
import com.bettigres.mi_1.ui.presentation.UserDataScreen
import com.bettigres.mi_1.ui.theme.MI_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MI_1Theme {
                BaseScreen()
            }
        }
    }
}