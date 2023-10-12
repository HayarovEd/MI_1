package com.fast.zai.loans.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.fast.zai.loans.data.RepositoryAnalyticImpl
import com.fast.zai.loans.data.RepositoryServerImpl

import com.fast.zai.loans.data.ServiceImpl
import com.fast.zai.loans.data.SharedKeeperImpl
import hed.hotzaem.tophh.gola.domain.RepositoryAnalytic
import com.fast.zai.loans.domain.RepositoryServer
import com.fast.zai.loans.domain.Service
import com.fast.zai.loans.domain.SharedKepper


@Module
@InstallIn(SingletonComponent::class)
abstract class DiModule {

    @Binds
    @Singleton
    abstract fun bindService(service: ServiceImpl): Service

    @Binds
    @Singleton
    abstract fun bindKeeper(sharedKeeper: SharedKeeperImpl): SharedKepper

    @Binds
    @Singleton
    abstract fun bindRepositoryAnalytic(repository: RepositoryAnalyticImpl): RepositoryAnalytic

    @Binds
    @Singleton
    abstract fun bindRepositoryServer(repository: RepositoryServerImpl): RepositoryServer

}