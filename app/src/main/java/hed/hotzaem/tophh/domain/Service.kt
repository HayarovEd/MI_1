package hed.hotzaem.tophh.domain

interface Service {
    val instanceIdMyTracker:String
    suspend fun getOAID(): String?

    suspend fun getHmsToken() : String?
    fun checkedInternetConnection(): Boolean
    fun sendAppsFlyerEvent(key: String, content:Map<String, String>)
    fun getYandexMetricaDeviceId (callback: (String?) -> Unit)
}