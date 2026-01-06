package com.vector.midtronicstest.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryUi(
    val flagUrl: String,
    val commonName: String,
    val cca2: String
)
