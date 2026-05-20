package com.example.mbsarfachruddin.model.remote.teacher.profile


import com.google.gson.annotations.SerializedName

data class TeacherResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)