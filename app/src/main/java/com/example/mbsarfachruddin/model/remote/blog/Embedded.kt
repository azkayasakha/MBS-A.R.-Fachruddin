package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class Embedded(
    @SerializedName("author")
    val author: List<com.example.mbsarfachruddin.model.remote.blog.Author>,
    @SerializedName("wp:featuredmedia")
    val wpFeaturedmedia: List<com.example.mbsarfachruddin.model.remote.blog.WpFeaturedmedia>,
    @SerializedName("wp:term")
    val wpTerm: List<List<com.example.mbsarfachruddin.model.remote.blog.WpTerm>>
)