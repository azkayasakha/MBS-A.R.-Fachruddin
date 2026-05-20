package com.example.mbsarfachruddin.model.remote.teacher.courseattendance


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("attendance_id")
    val attendanceId: Int?,
    @SerializedName("nisn")
    val nisn: String,
    @SerializedName("status")
    var status: String?,
    @SerializedName("student_name")
    val studentName: String
)