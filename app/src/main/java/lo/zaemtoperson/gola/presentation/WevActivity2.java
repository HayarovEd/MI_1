package lo.zaemtoperson.gola.presentation;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import lo.zaemtoperson.gola.InAppWebViewChromeClient;
import lo.zaemtoperson.gola.R;

public class WevActivity2 extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wev2);
        webView = findViewById(R.id.webView2);
        WebViewClient webViewClient = new WebViewClient();
        InAppWebViewChromeClient inAppWebViewChromeClient = new InAppWebViewChromeClient();
        inAppWebViewChromeClient.setActivity(this);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(inAppWebViewChromeClient);
        webView.loadUrl("https://ya.ru/");
    }

}