package com.vector.midtronicstest.data.mapper

import com.vector.midtronicstest.data.model.Country
import com.vector.midtronicstest.data.model.CountryUi
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long

fun mapResultToCountryObject(jsonObject: JsonObject): Country {
    val nameInfo = jsonObject["name"]

    val commonName = nameInfo?.jsonObject?.get("common")?.jsonPrimitive?.content ?: ""

    val officialName = nameInfo?.jsonObject?.get("official")?.jsonPrimitive?.content ?: ""

    val capital = jsonObject["capital"]?.jsonArray?.firstOrNull()?.jsonPrimitive?.content ?: ""

    val population = jsonObject["population"]?.jsonPrimitive?.long ?: 0L

    val region = jsonObject["region"]?.jsonPrimitive?.content ?: ""

    val subRegion = jsonObject["subregion"]?.jsonPrimitive?.content ?: ""

    val area = jsonObject["area"]?.jsonPrimitive?.double ?: 0.0

    val flags = jsonObject["flags"]

    val flagPng = flags?.jsonObject?.get("png")?.jsonPrimitive?.content ?: ""

    return Country(
        commonName = commonName,
        officialName = officialName,
        capital = capital,
        population = population,
        area = area,
        region = region,
        subRegion = subRegion,
        flagUrl = flagPng
    )
}

fun mapResultToCountryUiObject(jsonObject: JsonObject): CountryUi {
    val flags = jsonObject["flags"]

    val flagPng = flags?.jsonObject?.get("png")?.jsonPrimitive?.content ?: ""

    val nameInfo = jsonObject["name"]


    val commonName = nameInfo?.jsonObject?.get("common")?.jsonPrimitive?.content ?: ""

    val cca2 = jsonObject["cca2"]?.jsonPrimitive?.content ?: ""
    return CountryUi(
        flagUrl = flagPng,
        commonName = commonName,
        cca2 = cca2
    )
}