package lo.zaemtoperson.gola.domain.model.basedto


import com.google.gson.annotations.SerializedName

data class BaseDto(
    @SerializedName("app_config")
    val appConfig: AppConfig,
    @SerializedName("cards")
    val cards: List<Card>,
    @SerializedName("credits")
    val credits: List<Credit>,
    @SerializedName("loans")
    val loans: List<Loan>
)