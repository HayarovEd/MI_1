package lo.zaemtoperson.gola.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lo.zaemtoperson.gola.AppWorkerImpl
import javax.inject.Singleton
import lo.zaemtoperson.gola.data.RepositoryAnalyticImpl
import lo.zaemtoperson.gola.data.RepositoryServerImpl

import lo.zaemtoperson.gola.data.ServiceImpl
import lo.zaemtoperson.gola.data.SharedKeeperImpl
import lo.zaemtoperson.gola.domain.AppWorker
import lo.zaemtoperson.gola.domain.RepositoryAnalytic
import lo.zaemtoperson.gola.domain.RepositoryServer
import lo.zaemtoperson.gola.domain.Service
import lo.zaemtoperson.gola.domain.SharedKepper


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

    @Binds
    @Singleton
    abstract fun bindAppWorker(appWorker: AppWorkerImpl): AppWorker

}