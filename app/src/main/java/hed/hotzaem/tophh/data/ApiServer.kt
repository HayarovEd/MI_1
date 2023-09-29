package hed.hotzaem.tophh.data

import hed.hotzaem.tophh.domain.model.basedto.BaseDto
import retrofit2.http.GET

interface ApiServer {
    @GET ("441/db.json")
    suspend fun getDataDb () : BaseDto
}