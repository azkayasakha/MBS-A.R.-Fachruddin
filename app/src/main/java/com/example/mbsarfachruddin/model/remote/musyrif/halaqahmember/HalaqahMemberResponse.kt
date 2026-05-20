package com.example.mbsarfachruddin.model.remote.musyrif.halaqahmember


import com.google.gson.annotations.SerializedName

data class HalaqahMemberResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)