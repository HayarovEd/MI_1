package lo.zaemtoperson.gola.domain

import androidx.compose.runtime.MutableState

interface AppWorker {
    suspend fun getMyTracker(): String
    //val myTracker: MutableState<String>
    val appsFlyer:String
}