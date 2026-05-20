package com.example.mbsarfachruddin.model.remote.student.courseattendance


import com.google.gson.annotations.SerializedName

data class CourseAttendanceResponse(
    @SerializedName("data")
    val `data`: List<com.example.mbsarfachruddin.model.remote.student.courseattendance.Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)