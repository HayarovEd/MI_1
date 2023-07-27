package com.bettigres.mi_1.ui.presentation

sealed interface ChoiceCard{
    object NotChoice:ChoiceCard
    object ChoiceVosa:ChoiceCard
    object ChoiceMaestro:ChoiceCard
}