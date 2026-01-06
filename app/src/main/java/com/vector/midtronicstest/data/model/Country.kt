package com.vector.midtronicstest.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val commonName: String,
    val officialName: String,
    val capital: String,
    val population: Long,
    val area: Double,
    val region: String,
    val subRegion: String,
    val flagUrl: String
)


