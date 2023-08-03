package lo.zaemtoperson.gola.data

import android.app.Application
import android.content.Context
import android.telephony.TelephonyManager
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import javax.inject.Inject

class ServiceImpl @Inject constructor(val application: Application) {

    fun getSimCountryIso(): String? {
        val telephonyManager =
            application.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.simCountryIso
    }

    fun isRootedOne(): Boolean {
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

    fun isRootedTwo(): Boolean {
        return try {
            val buildTags = android.os.Build.TAGS
            buildTags != null && buildTags.contains("test-keys")
        } catch (e: Exception) {
            false
        }
    }

    fun isRootedThree(): Boolean {
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

    fun getGAID(context: Context): String? {
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
}