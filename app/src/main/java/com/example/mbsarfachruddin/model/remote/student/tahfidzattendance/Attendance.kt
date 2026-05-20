package com.example.mbsarfachruddin.model.remote.student.tahfidzattendance


import com.google.gson.annotations.SerializedName

data class Attendance(
    @SerializedName("Pagi")
    val pagi: String,
    @SerializedName("Sore")
    val sore: String
)