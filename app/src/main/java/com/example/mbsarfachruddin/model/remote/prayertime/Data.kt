package com.example.mbsarfachruddin.model.remote.prayertime

data class Data(
    val id: String,
    val jadwal: Map<String, JadwalHarian>,
    val kabko: String,
    val prov: String
)