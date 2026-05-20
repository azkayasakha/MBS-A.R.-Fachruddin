package com.example.mbsarfachruddin.model.remote.musyrif.tahfidz.create

import com.google.gson.annotations.SerializedName

data class TahfidzCreateResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)