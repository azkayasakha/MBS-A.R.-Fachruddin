package com.example.mbsarfachruddin.model.remote.musyrif.profile


import com.google.gson.annotations.SerializedName

data class MusyrifResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)