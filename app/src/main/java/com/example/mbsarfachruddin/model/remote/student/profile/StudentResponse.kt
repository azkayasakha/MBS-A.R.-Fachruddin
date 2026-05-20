package com.example.mbsarfachruddin.model.remote.student.profile


import com.google.gson.annotations.SerializedName

data class StudentResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)