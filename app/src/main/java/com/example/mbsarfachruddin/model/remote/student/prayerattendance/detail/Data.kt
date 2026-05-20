package com.example.mbsarfachruddin.model.remote.student.prayerattendance.detail


import com.google.gson.annotations.SerializedName

data class Data(
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