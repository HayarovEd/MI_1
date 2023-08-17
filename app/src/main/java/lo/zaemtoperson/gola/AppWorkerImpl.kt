package lo.zaemtoperson.gola

import android.util.Log
import kotlinx.coroutines.delay
import lo.zaemtoperson.gola.domain.AppWorker
import javax.inject.Inject

class AppWorkerImpl @Inject constructor(private val appGola: AppGola) : AppWorker {
    //override val myTracker = appGola.myTracker
    override suspend fun getMyTracker(): String {
        delay(1000)
        val tmp = appGola.getAppMyDeeplink()
        Log.d("ASDFGH", "myTracker worker -  ${tmp}")
        return tmp
    }

    override val appsFlyer = appGola.appsFlayer

}