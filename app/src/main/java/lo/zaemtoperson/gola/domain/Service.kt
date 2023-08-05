package lo.zaemtoperson.gola.domain

interface Service {
    val appMetrika: String
    val instanceIdMyTrcaker:String
    fun getSimCountryIso(): String?
    fun isRootedOne(): Boolean
    fun isRootedTwo(): Boolean
    fun isRootedThree(): Boolean
    fun getCurrentLocale(): String
    fun getDeviceAndroidId(): String?
    fun getFirebaseMessagingToken(callback: (String?) -> Unit)
    suspend fun getGAID(): String?
    fun getApplicationVersion(): String?
    fun checkedInternetConnection(): Boolean
    fun getMyTrackerDeeplink(callback: (String?) -> Unit)
    fun getAppsFlyerDeeplink(callback: (String?) -> Unit)

}