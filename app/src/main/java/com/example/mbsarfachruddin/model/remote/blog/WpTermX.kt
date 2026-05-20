package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class WpTermX(
    @SerializedName("embeddable")
    val embeddable: Boolean,
    @SerializedName("href")
    val href: String,
    @SerializedName("taxonomy")
    val taxonomy: String
)