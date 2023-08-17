package lo.zaemtoperson.gola.domain

interface AppWorker {
    suspend fun getMyTracker(): String
    //val myTracker: MutableState<String>
    val appsFlyer:String
}