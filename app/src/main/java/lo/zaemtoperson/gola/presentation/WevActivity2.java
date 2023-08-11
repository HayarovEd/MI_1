package lo.zaemtoperson.gola.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.Arrays;

import lo.zaemtoperson.gola.R;

public class WevActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wev2);
    }

    protected boolean needsCameraPermission() {
        boolean needed = false;


        PackageManager packageManager = this.getPackageManager();
        try {
            String[] requestedPermissions = packageManager.getPackageInfo(this.getApplicationContext().getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
            if (Arrays.asList(requestedPermissions).contains(android.Manifest.permission.CAMERA)
                    && ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                needed = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            needed = true;
        }

        return needed;
    }
}