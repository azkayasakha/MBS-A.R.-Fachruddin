package com.example.mbsarfachruddin.model.remote.teacher.courseattendance.update


import com.google.gson.annotations.SerializedName

data class CourseAttendanceUpdateResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)