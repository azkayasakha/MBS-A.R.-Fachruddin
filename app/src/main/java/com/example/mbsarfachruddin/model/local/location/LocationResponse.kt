package com.example.mbsarfachruddin.model.local.location

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    val status: Boolean,
    val message: String,
    @SerializedName("data")
    val locations: List<LocationData>
)
