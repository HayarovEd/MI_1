package lo.zaemtoperson.gola.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.appsflyer.AppsFlyerLib
import dagger.hilt.android.AndroidEntryPoint
import lo.zaemtoperson.gola.R
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import lo.zaemtoperson.gola.data.SHARED_APPSFLYER_INSTANCE_ID
import lo.zaemtoperson.gola.data.SHARED_DATA

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private var grantAccessCamera: MutableState<Boolean> = mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            grantAccessCamera.value = true
        } else {

        }
    }
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = application.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        val fromSharedAppsFlyer = sharedPref.getString(SHARED_APPSFLYER_INSTANCE_ID, "")
        val instanceIdAppsFlyer =  if (fromSharedAppsFlyer.isNullOrBlank()) {
            val instance = AppsFlyerLib.getInstance().getAppsFlyerUID(application)
            sharedPref.edit().putString(SHARED_APPSFLYER_INSTANCE_ID, instance).apply()
            instance
        } else {
            fromSharedAppsFlyer
        }
        //photoUri = Uri.EMPTY
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            Sample(instanceIdAppsFlyer = instanceIdAppsFlyer)
           /* BaseScreen(
                outputDirectory = outputDirectory,
                executor = cameraExecutor,
            )*/
        }
        //requestCameraPermission()

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                grantAccessCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            ) -> {}

            else -> requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
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
}