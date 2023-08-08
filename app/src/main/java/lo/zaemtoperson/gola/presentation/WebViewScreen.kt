package lo.zaemtoperson.gola.presentation

import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import lo.zaemtoperson.gola.ui.theme.baseBackground

@Composable
fun WebViewScreen(
    modifier: Modifier = Modifier,
    webView: WebView,
    url: String = "https://ya.ru/"
) {
    val qw = webView
    qw.loadUrl(url)
    AndroidView(
        modifier = modifier
            .fillMaxSize()
            .background(color = baseBackground)
            .padding(5.dp),
        factory = { qw })
}