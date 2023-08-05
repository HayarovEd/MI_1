package lo.zaemtoperson.gola.domain

import lo.zaemtoperson.gola.data.Resource

interface Repository {
    suspend fun getSub1 (
        applicationToken: String,
        userId: String,
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): Resource<String>
}