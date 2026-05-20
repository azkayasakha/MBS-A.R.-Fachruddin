package com.example.mbsarfachruddin.model.remote.student.prayerattendance


import com.google.gson.annotations.SerializedName

data class PrayerAttendanceResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)