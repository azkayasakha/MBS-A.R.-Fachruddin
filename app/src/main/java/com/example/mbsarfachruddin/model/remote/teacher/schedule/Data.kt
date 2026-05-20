package com.example.mbsarfachruddin.model.remote.teacher.schedule


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("class_id")
    val classId: String,
    @SerializedName("course_id")
    val courseId: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("subject")
    val subject: String
)