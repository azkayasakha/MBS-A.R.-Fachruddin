package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class Sizes(
    @SerializedName("full")
    val full: com.example.mbsarfachruddin.model.remote.blog.Full,
    @SerializedName("large")
    val large: com.example.mbsarfachruddin.model.remote.blog.Large,
    @SerializedName("medium")
    val medium: com.example.mbsarfachruddin.model.remote.blog.Medium,
    @SerializedName("medium_large")
    val mediumLarge: com.example.mbsarfachruddin.model.remote.blog.MediumLarge,
    @SerializedName("thumbnail")
    val thumbnail: com.example.mbsarfachruddin.model.remote.blog.Thumbnail,
    @SerializedName("1536x1536")
    val x1536: com.example.mbsarfachruddin.model.remote.blog.X1536
)