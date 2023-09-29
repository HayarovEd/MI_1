package hed.hotzaem.tophh.data

import android.app.Application
import android.content.Context
import javax.inject.Inject
import hed.hotzaem.tophh.domain.SharedKepper

class SharedKeeperImpl @Inject constructor(
    application: Application
): SharedKepper {
    private val sharedPref = application.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)

    override fun getFireBaseToken(): String? = sharedPref.getString(SHARED_FIREBASE_TOKEN, "")

    override fun setFireBaseToken(date: String) =
        sharedPref.edit().putString(SHARED_FIREBASE_TOKEN, date).apply()

    override fun getMyTrackerInstanceId(): String? = sharedPref.getString(
        SHARED_MY_TRACKER_INSTANCE_ID, "")

    override fun setMyTrackerInstanceId(date: String) =
        sharedPref.edit().putString(SHARED_MY_TRACKER_INSTANCE_ID, date).apply()

    override fun getAppsFlyerInstanceId(): String? = sharedPref.getString(
        SHARED_APPSFLYER_INSTANCE_ID, "")

    override fun setAppsFlyerInstanceId(date: String) =
        sharedPref.edit().putString(SHARED_APPSFLYER_INSTANCE_ID, date).apply()

    override fun getCurrentDate(): String? = sharedPref.getString(SHARED_DATE, "")

    override fun setCurrentDate(date: String) =
        sharedPref.edit().putString(SHARED_DATE, date).apply()

    override fun setSub2(date: String) =
        sharedPref.edit().putString(SHARED_SUB2, date).apply()

    override fun getSub2(): String? = sharedPref.getString(SHARED_SUB2, "")

    override fun setYandexMetricaDeviceId(date: String) =
        sharedPref.edit().putString(SHARED_YANDEX_DEVICE_ID, date).apply()

    override fun getYandexMetricaDeviceId(): String? = sharedPref.getString(SHARED_YANDEX_DEVICE_ID, "")

}