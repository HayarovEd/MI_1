package lo.zaemtoperson.gola.domain

import android.content.Context

interface Service {
    val appMetrika: String
    val instanceId:String
    fun getSimCountryIso(): String?
    fun isRootedOne(): Boolean
    fun isRootedTwo(): Boolean
    fun isRootedThree(): Boolean
    fun getCurrentLocale(): String
    fun getDeviceAndroidId(context: Context): String?
    fun getFirebaseMessagingToken(callback: (String?) -> Unit)
    fun getGAID(context: Context): String?
    fun getApplicationVersion(): String?
    fun checkedInternetConnection(): Boolean


}