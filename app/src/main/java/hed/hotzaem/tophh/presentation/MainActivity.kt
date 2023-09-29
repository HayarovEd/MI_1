package hed.hotzaem.tophh.gola.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.my.tracker.MyTracker
import dagger.hilt.android.AndroidEntryPoint
import lo.zaemtoperson.gola.R
import hed.hotzaem.tophh.gola.data.APPS_FLYER
import hed.hotzaem.tophh.gola.data.LINK
import hed.hotzaem.tophh.gola.data.SHARED_APPSFLYER_INSTANCE_ID
import hed.hotzaem.tophh.gola.data.SHARED_DATA
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private val viewModel: MainViewModel by viewModels()
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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                if (key== LINK) {
                    viewModel.loadLink(
                        link = value.toString(),
                    )
                }
            }
        }

        MyTracker.setAttributionListener {
            Log.d("ASDFGH", "myTracker activity $it")
            viewModel.loadMTDeeplink(
                deeplink = it.deeplink
            )
        }

        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                val appsFlayer =
                    conversionData.entries.joinToString(separator = ", ") { "${it.key}=${it.value}" }
                Log.d("ASDFGH", "temp -  $appsFlayer")
                viewModel.loadAFDeeplink(appsFlayer)
            }

            override fun onConversionDataFail(error: String?) {
                println("ASDFGH conversion error $error")
            }

            override fun onAppOpenAttribution(attributionData: MutableMap<String, String>?) {
                attributionData?.forEach {
                    println("ASDFGH attribution key ${it.key} valur ${it.value}")
                }
            }

            override fun onAttributionFailure(error: String?) {
                println("ASDFGH conversion error $error")
            }
        }
        val instanceId = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        val sharedPref = application.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        sharedPref.edit().putString(SHARED_APPSFLYER_INSTANCE_ID, instanceId).apply()
        /*AppsFlyerLib.getInstance().subscribeForDeepLink { deepLinkResult ->
            when (deepLinkResult.status) {
                DeepLinkResult.Status.FOUND -> {
                    Log.d(
                        "ASDFGH", "Deep link found1"
                    )
                }

                DeepLinkResult.Status.NOT_FOUND -> {
                    Log.d(
                        "ASDFGH", "Deep link not found1"
                    )
                }

                else -> {
                    val dlError = deepLinkResult.error
                    Log.d(
                        "ASDFGH", "There was an error getting Deep Link data1: $dlError"
                    )
                }
            }
            val deepLinkObj = deepLinkResult.deepLink
            try {
                viewModel.loadAFDeeplink(deepLinkObj.deepLinkValue ?: "")
                Log.d(
                    "ASDFGH", "The DeepLink data is1: $deepLinkObj"
                )
            } catch (e: Exception) {
                Log.d(
                    "ASDFGH", "DeepLink data came back null1"
                )
            }
            if (deepLinkObj.isDeferred == true) {
                Log.d("ASDFGH", "This is a deferred deep link1")
            } else {
                Log.d("ASDFGH", "This is a direct deep link1")
            }

            try {
                val fruitName = deepLinkObj.deepLinkValue
                viewModel.loadAFDeeplink(fruitName ?: "")
                Log.d("ASDFGH", "The DeepLink will route to1: $fruitName")
            } catch (e: Exception) {
                Log.d("ASDFGH", "There's been an error1: $e")
            }
        }*/
        AppsFlyerLib.getInstance().init(APPS_FLYER, conversionDataListener, this)
        AppsFlyerLib.getInstance().start(this)
        setContent {

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

    private fun askNotificationPermission() {
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