package com.example.mbsarfachruddin.model.remote.prayertime

data class JadwalHarian(
    val ashar: String,
    val dhuha: String,
    val dzuhur: String,
    val imsak: String,
    val isya: String,
    val maghrib: String,
    val subuh: String,
    val tanggal: String,
    val terbit: String
)