package hed.hotzaem.tophh.domain.model.basedto

import hed.hotzaem.tophh.domain.model.TypeCard

sealed class BaseState{
    object Loans: BaseState()
    class Cards(val typeCard: TypeCard): BaseState()
    object Credits: BaseState()
}
