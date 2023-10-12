package com.fast.zai.loans.domain

import com.fast.zai.loans.data.Resource
import com.fast.zai.loans.domain.model.basedto.BaseDto

interface RepositoryServer {
    suspend fun getDataDb() : Resource<BaseDto>
}