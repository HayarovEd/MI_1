package lo.zaemtoperson.gola

import lo.zaemtoperson.gola.domain.AppWorker
import javax.inject.Inject

class AppWorkerImpl @Inject constructor(appGola: AppGola) : AppWorker {
    override val myTracker = appGola.myTracker
    override val appsFlyer = appGola.appsFlayer

}