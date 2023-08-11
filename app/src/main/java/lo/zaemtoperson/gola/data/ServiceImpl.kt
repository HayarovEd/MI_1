package lo.zaemtoperson.gola.data

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.firebase.messaging.FirebaseMessaging
import com.my.tracker.MyTracker
import kotlinx.coroutines.tasks.await
import lo.zaemtoperson.gola.data.SecondFirebaseMessagingService.Companion.GROUP_ID
import lo.zaemtoperson.gola.data.SecondFirebaseMessagingService.Companion.GROUP_NAME
import lo.zaemtoperson.gola.domain.Service
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class ServiceImpl @Inject constructor(
    private val application: Application,) : Service {
    //P1
    override fun getSimCountryIso(): String? {
        val telephonyManager =
            application.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.simCountryIso
    }

    //P3
    override fun isRootedOne(): Boolean {
        val locations = arrayOf(
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )

        for (location in locations) {
            if (File(location).exists()) {
                return true
            }
        }

        return false
    }
    //P3
    override fun isRootedTwo(): Boolean {
        return try {
            val buildTags = Build.TAGS
            buildTags != null && buildTags.contains("test-keys")
        } catch (e: Exception) {
            false
        }
    }
    //P3
    override fun isRootedThree(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su")
            val outputStream = DataOutputStream(process.outputStream)
            outputStream.writeBytes("exit\n")
            outputStream.flush()
            process.waitFor()
            process.exitValue() == 0
        } catch (e: Exception) {
            false
        }
    }

    //P4
    override fun getCurrentLocale(): String {
        return Locale.getDefault().country
    }

    //P5
    override val appMetrika = APP_METRICA

    //P6
    @SuppressLint("HardwareIds")
    override fun getDeviceAndroidId(): String? {
        return Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID)
    }

    //P7
    override fun getFirebaseMessagingToken(callback: (String?) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    callback(token)
                } else {
                    callback(null)
                }
            }
    }

    //P8
    override suspend fun getGAID(): String? {
        return try {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(application)

            return adInfo.id
        } catch (e: IOException) {
            null
        } catch (e: GooglePlayServicesNotAvailableException) {
            null
        } catch (e: GooglePlayServicesRepairableException) {
            null
        }
    }

    //P9
    override val instanceIdMyTracker = MyTracker.getInstanceId(application)

    //P10
    override fun getApplicationVersion(): String? {
        return try {
            val packageInfo = application.packageManager.getPackageInfo(application.packageName, 0)
            packageInfo.versionName

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    override fun checkedInternetConnection(): Boolean {
        var result = false
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }


    override fun getMyTrackerDeeplink(callback: (String?) -> Unit) {
        MyTracker.initTracker(MY_TRACKER, application)
        MyTracker.setAttributionListener { attribution ->
            val deeplink = attribution.deeplink
            callback (deeplink)
        }
    }

    override fun getAppsFlyerDeeplink(callback: (String?) -> Unit) {

        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                val conversionId = conversionData["af_adset_id"] as? String
                callback(conversionId)
            }

            override fun onConversionDataFail(p0: String?) {
                TODO("Not yet implemented")
            }

            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
                TODO("Not yet implemented")
            }

            override fun onAttributionFailure(p0: String?) {
                TODO("Not yet implemented")
            }

            // ...
        }
        AppsFlyerLib.getInstance().init(APPS_FLYER, conversionDataListener, application)
        AppsFlyerLib.getInstance().start(application)
    }

    override fun sendAppsFlyerEvent(key: String, content:Map<String, String>) {
        AppsFlyerLib.getInstance().logEvent(application, key, content)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFireBasePush () {


        val notificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(GROUP_ID, GROUP_NAME))
        val channel = setChannel()
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setChannel(): NotificationChannel {


        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        channel.apply {
            enableLights(true)
            enableVibration(true)
            setShowBadge(true)
            group = GROUP_ID
            description = "This is a test description message for notification."
        }

        return channel
    }

}