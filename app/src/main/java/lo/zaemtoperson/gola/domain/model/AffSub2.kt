package lo.zaemtoperson.gola.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AffSub2(
    @SerializedName ("application_token")
    val applicationToken: String,
    @SerializedName ("user_id")
    val userId: String,
    @SerializedName ("payload_appsflyer")
    val appsflyer: String,
    @SerializedName ("payload_mytracker")
    val myTracker: String
): Serializable
