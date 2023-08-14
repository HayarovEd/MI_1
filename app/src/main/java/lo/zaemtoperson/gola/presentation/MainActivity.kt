package lo.zaemtoperson.gola.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.appsflyer.AppsFlyerLib
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.data.LINK
import lo.zaemtoperson.gola.data.SHARED_APPSFLYER_INSTANCE_ID
import lo.zaemtoperson.gola.data.SHARED_DATA


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private val requestPermissionLauncherFireBase = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                this,
                "FCM can't post notifications without POST_NOTIFICATIONS permission",
                Toast.LENGTH_LONG,
            ).show()
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()

        val sharedPref = application.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        val instance = AppsFlyerLib.getInstance().getAppsFlyerUID(application)
        sharedPref.edit().putString(SHARED_APPSFLYER_INSTANCE_ID, instance).apply()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        var link = ""
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                if (key==LINK) {
                    link = value.toString()
                }
            }
        }
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            viewModel.loadData(link = link)
            Sample(
                outputDirectory = outputDirectory,
                executor = cameraExecutor,
                viewModel = viewModel,
                )
        }

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    /*override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        }
    }*/

    private fun askNotificationPermission() {
        // This is only necessary for API Level > 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                requestPermissionLauncherFireBase.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

}