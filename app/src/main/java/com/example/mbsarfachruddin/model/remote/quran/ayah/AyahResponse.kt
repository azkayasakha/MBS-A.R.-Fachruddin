package com.example.mbsarfachruddin.model.remote.quran.ayah

import com.google.gson.annotations.SerializedName

data class AyahResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String
)