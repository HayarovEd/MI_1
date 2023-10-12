package com.fast.zai.loans.domain.model.basedto

import com.fast.zai.loans.domain.model.TypeCard

sealed class BaseState{
    object Loans: BaseState()
    class Cards(val typeCard: TypeCard): BaseState()
    class WebPrimary (
        val url: String,
        val offerName: String
    ): BaseState()
    object Credits: BaseState()
}
