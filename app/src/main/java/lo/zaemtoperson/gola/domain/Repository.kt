package lo.zaemtoperson.gola.domain

import lo.zaemtoperson.gola.data.Resource
import lo.zaemtoperson.gola.domain.model.Sub1
import lo.zaemtoperson.gola.domain.model.Sub3

interface Repository {
    suspend fun getSub1 (
        applicationToken: String,
        userId: String,
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): Resource<Sub1>

    suspend fun getSub3 (
        applicationToken: String,
        userId: String,
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String,
    ): Resource<Sub3>
}