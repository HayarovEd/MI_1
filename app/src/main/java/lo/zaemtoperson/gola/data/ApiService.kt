package lo.zaemtoperson.gola.data

import lo.zaemtoperson.gola.domain.model.AffSub1
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("subs/aff_sub1")
    suspend fun getSub1(@Body affSub1: AffSub1): String
}