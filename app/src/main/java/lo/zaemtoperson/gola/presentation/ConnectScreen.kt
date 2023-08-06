package lo.zaemtoperson.gola.presentation

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    text:String
) {

    Scaffold (

    ) {
        AndroidView(
            modifier = modifier.padding(it),
            factory = { context -> TextView(context) },
            update = { it.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT) }
        )
    }
}