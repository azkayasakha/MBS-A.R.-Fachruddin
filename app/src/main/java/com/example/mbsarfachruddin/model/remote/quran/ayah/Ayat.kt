package com.example.mbsarfachruddin.model.remote.quran.ayah


import com.google.gson.annotations.SerializedName

data class Ayat(
    @SerializedName("audio")
    val audio: Audio,
    @SerializedName("nomorAyat")
    val nomorAyat: Int,
    @SerializedName("teksArab")
    val teksArab: String,
    @SerializedName("teksIndonesia")
    val teksIndonesia: String,
    @SerializedName("teksLatin")
    val teksLatin: String
)