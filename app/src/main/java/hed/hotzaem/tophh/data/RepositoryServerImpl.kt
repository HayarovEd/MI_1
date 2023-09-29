package hed.hotzaem.tophh.gola.data

import android.util.Log
import javax.inject.Inject
import hed.hotzaem.tophh.gola.domain.RepositoryServer
import hed.hotzaem.tophh.gola.domain.model.CurrentDateDto
import hed.hotzaem.tophh.gola.domain.model.FolderPathDto
import hed.hotzaem.tophh.gola.domain.model.basedto.BaseDto

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
            Log.d("AABBCC", "sim $sim")
            Log.d("AABBCC", "colorFb $colorFb")
            Log.d("AABBCC", "root $root")
            Log.d("AABBCC", "local $local")
            Log.d("AABBCC", "metrikaKey $metrikaKey")
            Log.d("AABBCC", "deviceId $deviceId")
            Log.d("AABBCC", "fbKey $fbKey")
            Log.d("AABBCC", "gaid $gaid")
            Log.d("AABBCC", "instanceMyTracker $instanceMyTracker")
            Log.d("AABBCC", "version $version")
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