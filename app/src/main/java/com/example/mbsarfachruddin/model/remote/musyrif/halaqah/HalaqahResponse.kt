package com.example.mbsarfachruddin.model.remote.musyrif.halaqah


import com.google.gson.annotations.SerializedName

data class HalaqahResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)