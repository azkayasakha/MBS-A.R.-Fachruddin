package com.example.mbsarfachruddin.model.remote.student.classattendance


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("course_id")
    val courseId: String
)