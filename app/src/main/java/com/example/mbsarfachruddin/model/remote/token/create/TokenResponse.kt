package com.example.mbsarfachruddin.model.remote.token.create

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)