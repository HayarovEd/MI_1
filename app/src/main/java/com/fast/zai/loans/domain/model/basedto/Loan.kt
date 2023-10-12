package com.fast.zai.loans.domain.model.basedto


import com.google.gson.annotations.SerializedName

data class Loan(
    @SerializedName("description")
    val description: String,
    @SerializedName("extra_field_0")
    val extraField0: String,
    @SerializedName("extra_field_1")
    val extraField1: String,
    @SerializedName("hide_PercentFields")
    val hidePercentFields: String,
    @SerializedName("hide_TermFields")
    val hideTermFields: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("itemId")
    val itemId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: String,
    @SerializedName("orderButtonText")
    val orderButtonText: String,
    @SerializedName("percent")
    val percent: String,
    @SerializedName("percentPostfix")
    val percentPostfix: String,
    @SerializedName("percentPrefix")
    val percentPrefix: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("score")
    val score: String,
    @SerializedName("screen")
    val screen: String,
    @SerializedName("show_cash")
    val showCash: String,
    @SerializedName("show_mastercard")
    val showMastercard: String,
    @SerializedName("show_mir")
    val showMir: String,
    @SerializedName("show_qiwi")
    val showQiwi: String,
    @SerializedName("show_visa")
    val showVisa: String,
    @SerializedName("show_yandex")
    val showYandex: String,
    @SerializedName("summMax")
    val summMax: String,
    @SerializedName("summMid")
    val summMid: String,
    @SerializedName("summMin")
    val summMin: String,
    @SerializedName("summPostfix")
    val summPostfix: String,
    @SerializedName("summPrefix")
    val summPrefix: String,
    @SerializedName("termMax")
    val termMax: String,
    @SerializedName("termMid")
    val termMid: String,
    @SerializedName("termMin")
    val termMin: String,
    @SerializedName("termPostfix")
    val termPostfix: String,
    @SerializedName("termPrefix")
    val termPrefix: String
)