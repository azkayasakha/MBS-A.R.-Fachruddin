package com.example.mbsarfachruddin.model.remote.student.prayerattendance


import com.google.gson.annotations.SerializedName

data class Attendance(
    @SerializedName("Ashar")
    val ashar: String,
    @SerializedName("Dzuhur")
    val dzuhur: String,
    @SerializedName("Isya")
    val isya: String,
    @SerializedName("Maghrib")
    val maghrib: String,
    @SerializedName("Subuh")
    val subuh: String
)