package com.example.mbsarfachruddin.model.remote.announcement


import com.google.gson.annotations.SerializedName

data class AnnouncementResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)