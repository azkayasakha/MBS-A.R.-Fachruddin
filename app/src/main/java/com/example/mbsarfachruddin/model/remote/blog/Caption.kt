package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class Caption(
    @SerializedName("rendered")
    val rendered: String
)