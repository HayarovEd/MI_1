package lo.zaemtoperson.gola.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import lo.zaemtoperson.gola.InAppWebViewChromeClient;
import lo.zaemtoperson.gola.R;

public class WevActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wev2);
        InAppWebViewChromeClient inAppWebViewChromeClient = new InAppWebViewChromeClient();

    }

}