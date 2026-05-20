package com.example.mbsarfachruddin.model.remote.musyrif.tahfidz


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_checked")
    val isChecked: Int,
    @SerializedName("quran_juz")
    val quranJuz: Int,
    @SerializedName("quran_page")
    val quranPage: Int,
    @SerializedName("quran_page_section")
    val quranPageSection: Double,
    @SerializedName("type")
    val type: String
)