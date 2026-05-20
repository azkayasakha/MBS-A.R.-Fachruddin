package com.example.mbsarfachruddin.model.remote.student.tahfidzattendance


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("attendance")
    val attendance: Attendance,
    @SerializedName("date")
    val date: String
)