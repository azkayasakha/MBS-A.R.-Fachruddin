package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("code")
    val code: String,
    @SerializedName("data")
    val `data`: com.example.mbsarfachruddin.model.remote.blog.Data,
    @SerializedName("message")
    val message: String
)