package lo.zaemtoperson.gola

import android.app.Application
import com.my.tracker.MyTracker
import dagger.hilt.android.HiltAndroidApp
import lo.zaemtoperson.gola.data.MY_TRACKER


@HiltAndroidApp
class AppGola: Application() {
    override fun onCreate() {
        super.onCreate()
        //val trackerParams = MyTracker.getTrackerParams()
        //val trackerConfig = MyTracker.getTrackerConfig()
        MyTracker.initTracker(MY_TRACKER, this)
    }
}