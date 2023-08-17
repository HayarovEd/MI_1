package lo.zaemtoperson.gola

import android.util.Log
import kotlinx.coroutines.delay
import lo.zaemtoperson.gola.domain.AppWorker
import javax.inject.Inject

class AppWorkerImpl @Inject constructor(private val appGola: AppGola) : AppWorker {
    //override val myTracker = appGola.myTracker
    override suspend fun getMyTracker(): String {
        appGola
        delay(1000)
        Log.d("ASDFGH", "myTracker worker -  ${appGola.myTracker}")
        return appGola.myTracker
    }

    override val appsFlyer = appGola.appsFlayer

}