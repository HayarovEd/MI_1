package hed.hotzaem.tophh.domain

import hed.hotzaem.tophh.data.Resource
import hed.hotzaem.tophh.domain.model.CurrentDateDto
import hed.hotzaem.tophh.domain.model.FolderPathDto
import hed.hotzaem.tophh.domain.model.basedto.BaseDto

interface RepositoryServer {
    suspend fun getDataDb() : Resource<BaseDto>
}