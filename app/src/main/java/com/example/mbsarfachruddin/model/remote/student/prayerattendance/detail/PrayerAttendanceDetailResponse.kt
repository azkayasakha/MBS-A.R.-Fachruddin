package com.example.mbsarfachruddin.model.remote.student.prayerattendance.detail


import com.google.gson.annotations.SerializedName

data class PrayerAttendanceDetailResponse(
    @SerializedName("data")
    val `data`: com.example.mbsarfachruddin.model.remote.student.prayerattendance.detail.Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)