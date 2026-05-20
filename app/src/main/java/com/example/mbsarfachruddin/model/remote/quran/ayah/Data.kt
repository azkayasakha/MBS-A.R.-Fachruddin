package com.example.mbsarfachruddin.model.remote.quran.ayah


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("arti")
    val arti: String,
    @SerializedName("audioFull")
    val audioFull: AudioFull,
    @SerializedName("ayat")
    val ayat: List<Ayat>,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("jumlahAyat")
    val jumlahAyat: Int,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("namaLatin")
    val namaLatin: String,
    @SerializedName("nomor")
    val nomor: Int,
    @SerializedName("suratSebelumnya")
    val suratSebelumnya: SuratSebelumnya,
    @SerializedName("suratSelanjutnya")
    val suratSelanjutnya: SuratSelanjutnya,
    @SerializedName("tempatTurun")
    val tempatTurun: String
)