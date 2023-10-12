package com.fast.zai.loans.data

import com.fast.zai.loans.domain.model.basedto.BaseDto
import retrofit2.http.GET

interface ApiServer {
    @GET ("47e6ed64_Nov/db.json")
    suspend fun getDataDb () : BaseDto
}