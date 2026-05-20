package com.example.mbsarfachruddin.model.remote.musyrif.prayerattendance


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("attendance_id")
    val attendanceId: Int?,
    @SerializedName("nisn")
    val nisn: String,
    @SerializedName("student_name")
    val studentName: String,
    @SerializedName("status")
    var status: String?
)