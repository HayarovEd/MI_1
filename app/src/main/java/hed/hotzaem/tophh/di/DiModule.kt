package hed.hotzaem.tophh.gola.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import hed.hotzaem.tophh.gola.data.RepositoryAnalyticImpl
import hed.hotzaem.tophh.gola.data.RepositoryServerImpl

import hed.hotzaem.tophh.gola.data.ServiceImpl
import hed.hotzaem.tophh.gola.data.SharedKeeperImpl
import hed.hotzaem.tophh.gola.domain.RepositoryAnalytic
import hed.hotzaem.tophh.gola.domain.RepositoryServer
import hed.hotzaem.tophh.gola.domain.Service
import hed.hotzaem.tophh.gola.domain.SharedKepper


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