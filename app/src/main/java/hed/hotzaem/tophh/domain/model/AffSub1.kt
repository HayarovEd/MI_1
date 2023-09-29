package hed.hotzaem.tophh.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AffSub1(
    @SerializedName ("application_token")
    val applicationToken: String,
    @SerializedName ("user_id")
    val userId: String,
    @SerializedName ("payload_affsub1")
    val payloadAffsub: String
): Serializable
