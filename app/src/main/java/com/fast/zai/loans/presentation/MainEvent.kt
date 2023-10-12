package com.fast.zai.loans.presentation

import com.fast.zai.loans.domain.model.StatusApplication
import com.fast.zai.loans.domain.model.TypeCard
import com.fast.zai.loans.domain.model.basedto.BaseState

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
