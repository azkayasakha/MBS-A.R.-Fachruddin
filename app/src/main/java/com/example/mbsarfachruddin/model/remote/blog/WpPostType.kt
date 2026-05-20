package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class WpPostType(
    @SerializedName("href")
    val href: String
)