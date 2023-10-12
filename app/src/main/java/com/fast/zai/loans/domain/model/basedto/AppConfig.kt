package com.fast.zai.loans.domain.model.basedto


import com.google.gson.annotations.SerializedName

data class AppConfig(
    @SerializedName("privacy_policy_html")
    val privacyPolicyHtml: String,
    @SerializedName("show_calculator_item")
    val showCalculatorItem: String,
    @SerializedName("show_chat")
    val showChat: String,
    @SerializedName("show_news_item")
    val showNewsItem: String,
    @SerializedName("show_phone_authentication")
    val showPhoneAuthentication: String,
    @SerializedName("user_term_html")
    val userTermHtml: String,
    @SerializedName("extra_field_0")
    val showedIconPrimary: String?,
    @SerializedName("extra_field_1")
    val namePrimary: String?,
    @SerializedName("extra_field_2")
    val urlPrimary: String?,
)