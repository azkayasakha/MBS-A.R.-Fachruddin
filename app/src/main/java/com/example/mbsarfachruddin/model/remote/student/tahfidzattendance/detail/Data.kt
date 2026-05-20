package com.example.mbsarfachruddin.model.remote.student.tahfidzattendance.detail


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Pagi")
    val pagi: String,
    @SerializedName("Sore")
    val sore: String
)