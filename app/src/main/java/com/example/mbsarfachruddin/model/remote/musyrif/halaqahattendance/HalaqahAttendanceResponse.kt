package com.example.mbsarfachruddin.model.remote.musyrif.halaqahattendance


import com.google.gson.annotations.SerializedName

data class HalaqahAttendanceResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)