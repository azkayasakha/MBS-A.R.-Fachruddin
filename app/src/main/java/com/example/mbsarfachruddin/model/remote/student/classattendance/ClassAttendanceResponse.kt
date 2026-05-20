package com.example.mbsarfachruddin.model.remote.student.classattendance

import com.google.gson.annotations.SerializedName

data class ClassAttendanceResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)