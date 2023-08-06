package lo.zaemtoperson.gola.data

import android.app.Application
import android.content.Context
import javax.inject.Inject
import lo.zaemtoperson.gola.domain.SharedKepper

class SharedKeeperImpl @Inject constructor(
    application: Application
): SharedKepper {
    private val sharedPref = application.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)

    override fun getFireBaseToken(): String? = sharedPref.getString(SHARED_FIREBASE_TOKEN, "")

    override fun setFireBaseToken(date: String) =
        sharedPref.edit().putString(SHARED_FIREBASE_TOKEN, date).apply()

    override fun getMyTrackerInstanceId(): String? = sharedPref.getString(SHARED_MY_TRACKER_INSTANCE_ID, "")

    override fun setMyTrackerInstanceId(date: String) =
        sharedPref.edit().putString(SHARED_MY_TRACKER_INSTANCE_ID, date).apply()

    override fun getAppsFlyerInstanceId(): String? = sharedPref.getString(SHARED_APPSFLYER_INSTANCE_ID, "")

    override fun setAppsFlyerInstanceId(date: String) =
        sharedPref.edit().putString(SHARED_APPSFLYER_INSTANCE_ID, date).apply()

    override fun getCurrentDate(): String? = sharedPref.getString(SHARED_DATE, "")

    override fun setCurrentDate(date: String) =
        sharedPref.edit().putString(SHARED_DATE, date).apply()

}