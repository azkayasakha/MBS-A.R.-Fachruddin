package com.example.mbsarfachruddin.model.remote.student.tahfidz


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("quran_juz")
    val quranJuz: Int,
    @SerializedName("quran_page")
    val quranPage: Int,
    @SerializedName("quran_page_section")
    val quranPageSection: Double,
    @SerializedName("type")
    val type: String,
    @SerializedName("is_checked")
    var isChecked: Int
)