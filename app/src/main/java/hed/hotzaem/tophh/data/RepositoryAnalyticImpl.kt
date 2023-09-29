package hed.hotzaem.tophh.gola.data

import com.google.gson.Gson
import javax.inject.Inject
import hed.hotzaem.tophh.gola.domain.RepositoryAnalytic
import hed.hotzaem.tophh.gola.domain.model.AffSub1
import hed.hotzaem.tophh.gola.domain.model.AffSub2
import hed.hotzaem.tophh.gola.domain.model.AffSub3
import hed.hotzaem.tophh.gola.domain.model.AffSub5
import hed.hotzaem.tophh.gola.domain.model.Sub1
import hed.hotzaem.tophh.gola.domain.model.Sub2
import hed.hotzaem.tophh.gola.domain.model.Sub3
import hed.hotzaem.tophh.gola.domain.model.Sub5


class RepositoryAnalyticImpl @Inject constructor(
    private val apiAnalytic: ApiAnalytic
): RepositoryAnalytic {

    override suspend fun getSub1(
        applicationToken: String,
        userId: String,
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): Resource<Sub1> {
        return try {
            val jsonData = apiAnalytic.getSub1(
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

    override suspend fun getSub2(
        applicationToken: String,
        userId: String,
        appsflyer: String,
        myTracker: String
    ): Resource<Sub2> {
        return try {
            val jsonData = apiAnalytic.getSub2(
                AffSub2(
                    applicationToken = applicationToken,
                    userId = userId,
                    appsflyer = appsflyer,
                    myTracker = myTracker
                )
            )
            val gson = Gson()
            Resource.Success(
                data = gson.fromJson(jsonData, Sub2::class.java)
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
            val jsonData = apiAnalytic.getSub3(
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
            val jsonData = apiAnalytic.getSub5(
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
