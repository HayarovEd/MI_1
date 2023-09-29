package hed.hotzaem.tophh.gola.domain.model.basedto


import com.google.gson.annotations.SerializedName

data class BaseDto(
    @SerializedName("app_config")
    val appConfig: AppConfig,
    @SerializedName("cards")
    val cards: List<Card> = emptyList(),
    @SerializedName("credits")
    val credits: List<Credit> = emptyList(),
    @SerializedName("loans")
    val loans: List<Loan> = emptyList()
)