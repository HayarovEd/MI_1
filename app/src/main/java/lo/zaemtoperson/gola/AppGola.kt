package lo.zaemtoperson.gola

import android.app.Application
import com.appsflyer.AppsFlyerLib
import com.my.tracker.MyTracker
import dagger.hilt.android.HiltAndroidApp
import lo.zaemtoperson.gola.data.APPS_FLYER
import lo.zaemtoperson.gola.data.MY_TRACKER
import lo.zaemtoperson.gola.data.USER_X
import pro.userx.UserX


@HiltAndroidApp
class AppGola: Application() {
    override fun onCreate() {
        super.onCreate()
        MyTracker.initTracker(MY_TRACKER, this)
        AppsFlyerLib.getInstance().init(APPS_FLYER, null, this)
        UserX.init(USER_X)
    }
}