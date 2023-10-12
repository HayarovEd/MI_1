package com.fast.zai.loans.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.fast.zai.loans.data.ApiAnalytic
import com.fast.zai.loans.data.ApiServer
import com.fast.zai.loans.data.URL_ANALYTICS
import com.fast.zai.loans.data.URL_SERVER
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideApiAnalytic(): ApiAnalytic {
        return Retrofit.Builder()
            .baseUrl(URL_ANALYTICS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiAnalytic::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServer(): ApiServer {
        return Retrofit.Builder()
            .baseUrl(URL_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServer::class.java)
    }
}