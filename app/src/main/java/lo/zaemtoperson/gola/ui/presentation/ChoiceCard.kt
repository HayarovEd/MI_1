package lo.zaemtoperson.gola.ui.presentation

sealed interface ChoiceCard{
    object NotChoice: ChoiceCard
    object ChoiceVisa: ChoiceCard
    object ChoiceMaestro: ChoiceCard
}