package hed.hotzaem.tophh.domain

interface SharedKepper {

    fun getFireBaseToken(): String?

    fun setFireBaseToken(date: String)

    fun getMyTrackerInstanceId(): String?

    fun setMyTrackerInstanceId(date: String)

    fun getAppsFlyerInstanceId(): String?

    fun setAppsFlyerInstanceId(date: String)

    fun getCurrentDate(): String?

    fun setCurrentDate(date: String)

    fun setSub2(date: String)

    fun getSub2(): String?

    fun setYandexMetricaDeviceId(date: String)

    fun getYandexMetricaDeviceId(): String?
}