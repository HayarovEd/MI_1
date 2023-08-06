package lo.zaemtoperson.gola.data

import javax.inject.Inject
import lo.zaemtoperson.gola.domain.RepositoryServer
import lo.zaemtoperson.gola.domain.model.CurrentDateDto
import lo.zaemtoperson.gola.domain.model.FolderPathDto
import lo.zaemtoperson.gola.domain.model.basedto.BaseDto

class RepositoryServerImpl @Inject constructor(
    private val apiServer: ApiServer
) : RepositoryServer {
    override suspend fun getFolder(
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
    ) : Resource<FolderPathDto> {
        return try {
            val folder = apiServer.getFolder(
                sim = sim,
                colorFb = colorFb,
                root = root,
                local = local,
                metrikaKey = metrikaKey,
                deviceId = deviceId,
                fbKey = fbKey,
                gaid = gaid,
                instanceMyTracker = instanceMyTracker,
                version = version
            )
            Resource.Success(
                data = folder
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error")
        }
    }

    override suspend fun getCurrentDate(date:String) : Resource<CurrentDateDto> {
        return try {
            val folder = apiServer.getCurrentDate(date)
            Resource.Success(
                data = folder
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error")
        }
    }

    override suspend fun getDataDb(db:String) : Resource<BaseDto> {
        return try {
            val folder = apiServer.getDataDb(db)
            Resource.Success(
                data = folder
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error")
        }
    }
}