package lo.zaemtoperson.gola.data

import com.google.gson.Gson
import javax.inject.Inject
import lo.zaemtoperson.gola.domain.Repository
import lo.zaemtoperson.gola.domain.model.AffSub1
import lo.zaemtoperson.gola.domain.model.AffSub3
import lo.zaemtoperson.gola.domain.model.AffSub5
import lo.zaemtoperson.gola.domain.model.Sub1
import lo.zaemtoperson.gola.domain.model.Sub3
import lo.zaemtoperson.gola.domain.model.Sub5


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
    ): Resource<Sub1> {
        return try {
            val jsonData = apiService.getSub1(
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
            val gson = Gson()
            Resource.Success(
                data = gson.fromJson(jsonData, Sub1::class.java)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error")
        }
    }

    override suspend fun getSub3(
        applicationToken: String,
        userId: String,
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): Resource<Sub3> {
        return try {
            val jsonData = apiService.getSub3(
                AffSub3(
                    applicationToken = applicationToken,
                    userId = userId,
                    payloadAffsub = createPayloadAffsub3(
                        myTrackerId = myTrackerId,
                        appMetricaId = appMetricaId,
                        appsflyer = appsflyer,
                        firebaseToken = firebaseToken
                    )
                )
            )
            val gson = Gson()
            Resource.Success(
                data = gson.fromJson(jsonData, Sub3::class.java)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error")
        }
    }

    override suspend fun getSub5(
        applicationToken: String,
        userId: String,
        gaid: String
    ): Resource<Sub5> {
        return try {
            val jsonData = apiService.getSub5(
                AffSub5(
                    applicationToken = applicationToken,
                    userId = userId,
                    payloadAffsub = createPayloadAffsub5(
                        gaid = gaid
                    )
                )
            )
            val gson = Gson()
            Resource.Success(
                data = gson.fromJson(jsonData, Sub5::class.java)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error")
        }
    }

    private fun createPayloadAffsub1(
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): String =
        "{\"AppMetricaDeviceID\":\"$appMetricaId\",\"Appsflyer\":\"$appsflyer\",\"FireBase\":\"${
            firebaseToken.substring(
                0,
                30
            )
        }\",\"MyTracker\":\"$myTrackerId\"}"

    private fun createPayloadAffsub3(
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): String =
        "{\"AppMetricaDeviceID\":\"$appMetricaId\",\"Appsflyer\":\"$appsflyer\",\"FireBaseToken\":\"${firebaseToken}\",\"MyTracker\":\"$myTrackerId\"}"


    private fun createPayloadAffsub5(
        gaid: String
    ): String =
        "{\"GAID\":\"$gaid\"}"
}
