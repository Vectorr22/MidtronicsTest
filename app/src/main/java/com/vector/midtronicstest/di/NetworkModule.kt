package com.vector.midtronicstest.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.vector.midtronicstest.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    private val networkJson = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    fun provideBaseUrl(): String = "https://restcountries.com/v3.1/"

    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


}