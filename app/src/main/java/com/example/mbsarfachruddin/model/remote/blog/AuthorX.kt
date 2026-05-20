package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class AuthorX(
    @SerializedName("embeddable")
    val embeddable: Boolean,
    @SerializedName("href")
    val href: String
)