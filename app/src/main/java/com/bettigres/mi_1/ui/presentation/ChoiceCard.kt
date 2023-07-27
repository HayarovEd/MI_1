package com.bettigres.mi_1.ui.presentation

sealed interface ChoiceCard{
    object NotChoice:ChoiceCard
    object ChoiceVisa:ChoiceCard
    object ChoiceMaestro:ChoiceCard
}