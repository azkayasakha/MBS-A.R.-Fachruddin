package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("about")
    val about: List<com.example.mbsarfachruddin.model.remote.blog.About>,
    @SerializedName("collection")
    val collection: List<com.example.mbsarfachruddin.model.remote.blog.Collection>,
    @SerializedName("curies")
    val curies: List<com.example.mbsarfachruddin.model.remote.blog.Cury>,
    @SerializedName("self")
    val self: List<com.example.mbsarfachruddin.model.remote.blog.Self>,
    @SerializedName("wp:post_type")
    val wpPostType: List<com.example.mbsarfachruddin.model.remote.blog.WpPostType>
)