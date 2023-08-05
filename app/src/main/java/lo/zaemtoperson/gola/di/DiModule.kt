package lo.zaemtoperson.gola.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import lo.zaemtoperson.gola.data.ApiService
import lo.zaemtoperson.gola.data.BASE_URL
import lo.zaemtoperson.gola.data.RepositoryImpl

import lo.zaemtoperson.gola.data.ServiceImpl
import lo.zaemtoperson.gola.data.SharedKeeperImpl
import lo.zaemtoperson.gola.domain.Repository
import lo.zaemtoperson.gola.domain.Service
import lo.zaemtoperson.gola.domain.SharedKepper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
    abstract fun bindRepository(repository: RepositoryImpl): Repository
}