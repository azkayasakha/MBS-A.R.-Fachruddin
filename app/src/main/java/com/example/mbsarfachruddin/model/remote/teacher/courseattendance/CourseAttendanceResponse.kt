package com.example.mbsarfachruddin.model.remote.teacher.courseattendance


import com.google.gson.annotations.SerializedName

data class CourseAttendanceResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)