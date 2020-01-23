package com.shadow.moviecatalogue.data.remote.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shadow.moviecatalogue.data.remote.service.CatalogueService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient().create()
    }

    @Provides
    @Singleton
    @Named("catalogue")
    fun provideRetrofit(OkHttpClient: OkHttpClient, Gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiKeys.BASE_URL)
            .client(OkHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }

    @Provides
    @Singleton
    internal fun provideCatalogueService(@Named("catalogue") retrofit: Retrofit):
            CatalogueService = retrofit.create(CatalogueService::class.java)
}