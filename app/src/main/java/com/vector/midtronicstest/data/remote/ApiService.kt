package com.vector.midtronicstest.data.remote

import kotlinx.serialization.json.JsonArray
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("all")
    suspend fun getAllCountriesResult(
        @Query("fields") fields: String
    ): JsonArray

    @GET("name/{name}")
    suspend fun getSpecificCountry(
        @Path("name") countryName: String,
        @Query("fields") fields: String
    ): JsonArray
}