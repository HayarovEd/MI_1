package lo.zaemtoperson.gola.data

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.firebase.messaging.FirebaseMessaging
import com.my.tracker.MyTracker
import lo.zaemtoperson.gola.domain.Service
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class ServiceImpl @Inject constructor(val application: Application): Service {
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
            val buildTags = android.os.Build.TAGS
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
    override fun getDeviceAndroidId(context: Context): String? {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
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
    override fun getGAID(context: Context): String? {
        return try {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)

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
    override val instanceId = MyTracker.getInstanceId(application)

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
}