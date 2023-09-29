package hed.hotzaem.tophh.gola.ui.presentation_v1

sealed interface ScreenState{
    object Card: ScreenState
    object BaseData: ScreenState
    object DateTime: ScreenState
    object Selfie: ScreenState
    object Confirm: ScreenState
}