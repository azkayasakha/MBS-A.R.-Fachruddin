package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class WpTerm(
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("_links")
    val links: com.example.mbsarfachruddin.model.remote.blog.LinksX,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("taxonomy")
    val taxonomy: String
)