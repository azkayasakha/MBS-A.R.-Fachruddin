package com.example.mbsarfachruddin.model.remote.login


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("reference_id")
    val referenceId: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("username")
    val username: String
)