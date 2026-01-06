package com.vector.midtronicstest.data.repository

import android.util.Log
import com.vector.midtronicstest.data.mapper.mapResultToCountryObject
import com.vector.midtronicstest.data.mapper.mapResultToCountryUiObject
import com.vector.midtronicstest.data.model.Country
import com.vector.midtronicstest.data.model.CountryUi
import com.vector.midtronicstest.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.jsonObject
import javax.inject.Inject

class OnlineRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAllCountriesUi(): Result<List<CountryUi>> {
        return withContext(Dispatchers.IO) {
            try {
                val responseArray = apiService.getAllCountriesResult(
                    fields = "name,flags,cca2"
                )

                val resultToCountriesUi = responseArray.map { element ->
                    val jsonObject = element.jsonObject
                    mapResultToCountryUiObject(jsonObject)
                }

                Log.d("dev", "success fetching")
                Result.success(resultToCountriesUi)

            } catch (e: Exception) {
                Log.e("error", "error fetching", e)
                Result.failure(exception = e)
            }
        }
    }

    suspend fun getSpecificCountry(
        countryName: String
    ): Result<Country> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.getSpecificCountry(
                    countryName = countryName,
                    fields = "name,capital,population,area,region,subregion,flags"
                )
                val firstMatchResponse = result.first().jsonObject
                val resultToCountry = mapResultToCountryObject(firstMatchResponse)
                Result.success(resultToCountry)

            } catch (e: Exception) {
                Log.e("error", "error fetching", e)
                Result.failure(exception = e)
            }
        }
    }
}