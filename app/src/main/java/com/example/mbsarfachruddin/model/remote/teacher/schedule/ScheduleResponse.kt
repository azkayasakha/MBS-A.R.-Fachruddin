package com.example.mbsarfachruddin.model.remote.teacher.schedule


import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)