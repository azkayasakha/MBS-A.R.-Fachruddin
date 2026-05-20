package com.example.mbsarfachruddin.model.remote.login.update


import com.google.gson.annotations.SerializedName

data class LoginUpdateResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String
)