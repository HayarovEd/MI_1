package lo.zaemtoperson.gola.domain.model

import android.net.Uri

data class Notification(
    val icon: String?,
    val minImage: Uri?,
    val title: String?,
    val message: String?,
    val image: Uri?
)