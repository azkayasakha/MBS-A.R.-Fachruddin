package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class Self(
    @SerializedName("href")
    val href: String,
    @SerializedName("targetHints")
    val targetHints: com.example.mbsarfachruddin.model.remote.blog.TargetHints
)