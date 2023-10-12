package com.fast.zai.loans.ui.presentation_v1

sealed interface ChoiceCard{
    object NotChoice: ChoiceCard
    object ChoiceVisa: ChoiceCard
    object ChoiceMaestro: ChoiceCard
}