package com.example.mbsarfachruddin.model.remote.notification


import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String
)