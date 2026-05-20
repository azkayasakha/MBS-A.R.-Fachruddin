package com.example.mbsarfachruddin.model.remote.quran.ayah


import com.google.gson.annotations.SerializedName

data class SuratSelanjutnya(
    @SerializedName("jumlahAyat")
    val jumlahAyat: Int,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("namaLatin")
    val namaLatin: String,
    @SerializedName("nomor")
    val nomor: Int
)