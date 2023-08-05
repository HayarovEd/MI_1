package lo.zaemtoperson.gola.domain.model.basedto


import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("cards_credit")
    val cardsCredit: List<CardsCredit>,
    @SerializedName("cards_debit")
    val cardsDebit: List<CardsDebit>,
    @SerializedName("cards_installment")
    val cardsInstallment: List<CardsInstallment>
)