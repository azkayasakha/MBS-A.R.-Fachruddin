package com.example.mbsarfachruddin.model.remote.student.tahfidzattendance


import com.google.gson.annotations.SerializedName

data class TahfidzAttendanceResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)