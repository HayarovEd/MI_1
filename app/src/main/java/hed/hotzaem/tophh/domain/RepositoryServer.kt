package hed.hotzaem.tophh.gola.domain

import hed.hotzaem.tophh.gola.data.Resource
import hed.hotzaem.tophh.gola.domain.model.CurrentDateDto
import hed.hotzaem.tophh.gola.domain.model.FolderPathDto
import hed.hotzaem.tophh.gola.domain.model.basedto.BaseDto

interface RepositoryServer {
    suspend fun getFolder(
        sim: String,
        colorFb: String,
        root: String,
        local: String,
        metrikaKey: String,
        deviceId: String,
        fbKey: String,
        gaid: String,
        instanceMyTracker: String,
        version: String,
    ) : Resource<FolderPathDto>

    suspend fun getCurrentDate(date:String) : Resource<CurrentDateDto>

    suspend fun getDataDb(db:String) : Resource<BaseDto>
}