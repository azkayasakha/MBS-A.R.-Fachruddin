package com.example.mbsarfachruddin.model.remote.musyrif.tahfidz.delete


import com.google.gson.annotations.SerializedName

data class TahfidzDeleteResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)