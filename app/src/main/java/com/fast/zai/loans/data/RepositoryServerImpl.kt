package com.fast.zai.loans.data

import android.util.Log
import com.fast.zai.loans.data.Resource.Error
import com.fast.zai.loans.data.Resource.Success
import javax.inject.Inject
import com.fast.zai.loans.domain.RepositoryServer
import com.fast.zai.loans.domain.model.basedto.BaseDto

class RepositoryServerImpl @Inject constructor(
    private val apiServer: ApiServer
) : RepositoryServer {
    override suspend fun getDataDb(): Resource<BaseDto> {
        return try {
            val folder = apiServer.getDataDb()
            Log.d("DATADB", "dATA DB:${folder.loans.first().id}")
            Success(
                data = folder
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Error(e.message ?: "An unknown error")
        }
    }
}