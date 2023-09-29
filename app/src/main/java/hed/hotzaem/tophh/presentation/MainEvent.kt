package hed.hotzaem.tophh.presentation

import hed.hotzaem.tophh.domain.model.StatusApplication
import hed.hotzaem.tophh.domain.model.TypeCard
import hed.hotzaem.tophh.domain.model.basedto.BaseState

sealed class MainEvent {
    object Reconnect: MainEvent()

    class OnChangeStatusApplication(val statusApplication: StatusApplication): MainEvent()
    class OnChangeBaseState(val baseState: BaseState): MainEvent()
    class OnChangeTypeCard(val typeCard: TypeCard): MainEvent()

    class OnGoToWeb(
        val urlOffer: String,
        val nameOffer: String
        ): MainEvent()
}
