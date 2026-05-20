package com.example.mbsarfachruddin.model.remote.student.courseattendance


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("status")
    val status: String
)