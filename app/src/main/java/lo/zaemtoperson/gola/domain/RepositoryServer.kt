package lo.zaemtoperson.gola.domain

import lo.zaemtoperson.gola.data.Resource
import lo.zaemtoperson.gola.domain.model.CurrentDateDto
import lo.zaemtoperson.gola.domain.model.FolderPathDto
import lo.zaemtoperson.gola.domain.model.basedto.BaseDto

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