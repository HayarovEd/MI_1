package hed.hotzaem.tophh.gola.data

import hed.hotzaem.tophh.gola.domain.model.CurrentDateDto
import hed.hotzaem.tophh.gola.domain.model.FolderPathDto
import hed.hotzaem.tophh.gola.domain.model.basedto.BaseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServer {
    @GET ("auth")
    suspend fun getFolder(
        @Query("p1") sim: String,
        @Query("p2") colorFb: String,
        @Query("p3") root: String,
        @Query("p4") local: String,
        @Query("p5") metrikaKey: String,
        @Query("p6") deviceId: String,
        @Query("p7") fbKey: String,
        @Query("p8") gaid: String,
        @Query("p9") instanceMyTracker: String,
        @Query("p10") version: String,
    ) : FolderPathDto

    @GET ("{actualbackend}/date.json")
    suspend fun getCurrentDate (
        @Path ("actualbackend") path: String
    ) : CurrentDateDto

    @GET ("{actualbackend}/db.json")
    suspend fun getDataDb (
        @Path ("actualbackend") path: String
    ) : BaseDto
}