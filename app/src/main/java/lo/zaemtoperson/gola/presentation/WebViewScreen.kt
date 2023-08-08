package lo.zaemtoperson.gola.presentation

import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import lo.zaemtoperson.gola.ui.theme.baseBackground
import lo.zaemtoperson.gola.ui.theme.green

@Composable
fun WebViewScreen(
    modifier: Modifier = Modifier,
    webView: WebView,
    url: String
) {
    val isLoadingUrl: MutableState<Boolean> = remember {
        mutableStateOf(true)
    }
    webView.loadUrl(url)
    webView.webViewClient = WebViewClientHand(isLoadingUrl = isLoadingUrl)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = baseBackground)
            .padding(5.dp)
    ) {
        AndroidView(
            modifier = modifier
                .fillMaxSize()
                .background(color = baseBackground)
                .padding(5.dp),
            factory = { webView })
        if (isLoadingUrl.value) {
            CircularProgressIndicator(
                modifier = modifier
                    .size(100.dp)
                    .align(alignment = Alignment.Center),
                color = green
            )
        }
    }
}

class WebViewClientHand(val isLoadingUrl: MutableState<Boolean>) : android.webkit.WebViewClient() {

    // Load the URL
    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return false
    }

    // ProgressBar will disappear once page is loaded
    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        isLoadingUrl.value = false
    }
}