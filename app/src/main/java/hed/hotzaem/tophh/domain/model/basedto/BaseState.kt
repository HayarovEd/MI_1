package hed.hotzaem.tophh.gola.domain.model.basedto

import hed.hotzaem.tophh.gola.domain.model.TypeCard

sealed class BaseState{
    object Loans: BaseState()
    class Cards(val typeCard: TypeCard): BaseState()
    object Credits: BaseState()
}
