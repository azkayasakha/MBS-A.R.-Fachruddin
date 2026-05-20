package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class WpAttachment(
    @SerializedName("href")
    val href: String
)