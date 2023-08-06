package lo.zaemtoperson.gola.data

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.Html

fun String.convertHtmlToText(): String = if (VERSION.SDK_INT >= VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
} else {
    Html.fromHtml(this).toString()
}