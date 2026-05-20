package com.example.mbsarfachruddin.model.remote.musyrif.prayerattendance.update


import com.google.gson.annotations.SerializedName

data class PrayerAttendanceUpdateResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)