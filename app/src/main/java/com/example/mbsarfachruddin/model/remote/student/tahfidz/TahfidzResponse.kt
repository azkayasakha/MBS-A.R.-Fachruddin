package com.example.mbsarfachruddin.model.remote.student.tahfidz

import com.google.gson.annotations.SerializedName

data class TahfidzResponse(
    @SerializedName("data")
    val `data`: List<Data>?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)