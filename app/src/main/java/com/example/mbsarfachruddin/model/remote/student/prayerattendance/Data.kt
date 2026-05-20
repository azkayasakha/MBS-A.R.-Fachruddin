package com.example.mbsarfachruddin.model.remote.student.prayerattendance

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("date")
    val date: String,
    @SerializedName("attendance")
    val attendance: Attendance
)