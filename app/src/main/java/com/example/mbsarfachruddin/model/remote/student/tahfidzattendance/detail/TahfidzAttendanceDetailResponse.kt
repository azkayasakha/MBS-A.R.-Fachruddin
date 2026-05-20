package com.example.mbsarfachruddin.model.remote.student.tahfidzattendance.detail


import com.google.gson.annotations.SerializedName

data class TahfidzAttendanceDetailResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)