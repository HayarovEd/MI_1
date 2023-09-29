package hed.hotzaem.tophh.gola.domain.model.basedto


import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("cards_credit")
    val cardsCredit: List<CardsCredit> = emptyList(),
    @SerializedName("cards_debit")
    val cardsDebit: List<CardsDebit> = emptyList(),
    @SerializedName("cards_installment")
    val cardsInstallment: List<CardsInstallment> = emptyList()
)