package com.example.mbsarfachruddin.model.remote.musyrif.halaqahattendance.update


import com.google.gson.annotations.SerializedName

data class HalaqahAttendanceUpdateResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)