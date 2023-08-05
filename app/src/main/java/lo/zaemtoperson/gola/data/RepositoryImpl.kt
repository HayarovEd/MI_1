package lo.zaemtoperson.gola.data

import javax.inject.Inject
import lo.zaemtoperson.gola.domain.Repository
import lo.zaemtoperson.gola.domain.model.AffSub1

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
): Repository {

    override suspend fun getSub1(
        applicationToken: String,
        userId: String,
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): Resource<String> {
        return try {
            Resource.Success(
                data = apiService.getSub1(
                    AffSub1(
                        applicationToken = applicationToken,
                        userId = userId,
                        payloadAffsub = createPayloadAffsub1(
                            myTrackerId = myTrackerId,
                            appMetricaId = appMetricaId,
                            appsflyer = appsflyer,
                            firebaseToken = firebaseToken
                        )
                    )
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error")
        }
    }

    private fun createPayloadAffsub1 (
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): String = "{\"AppMetricaDeviceID\":\"$appMetricaId\",\"Appsflyer\":\"$appsflyer\",\"FireBase\":\"${firebaseToken.substring(0,30)}\",\"MyTracker\":\"$myTrackerId\"}"
}
