package lo.zaemtoperson.gola.ui.presentation

sealed interface ScreenState{
    object Card: ScreenState
    object BaseData: ScreenState
    object DateTime: ScreenState
    object Selfie: ScreenState
    object Confirm: ScreenState
}