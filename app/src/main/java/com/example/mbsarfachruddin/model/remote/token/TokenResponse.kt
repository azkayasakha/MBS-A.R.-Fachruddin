package com.example.mbsarfachruddin.model.remote.token


import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("data")
    val `data`: List<String>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)