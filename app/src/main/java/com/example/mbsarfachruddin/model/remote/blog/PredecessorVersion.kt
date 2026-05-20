package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class PredecessorVersion(
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: Int
)