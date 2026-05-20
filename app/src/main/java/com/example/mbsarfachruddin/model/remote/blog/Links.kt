package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("about")
    val about: List<com.example.mbsarfachruddin.model.remote.blog.About>,
    @SerializedName("author")
    val author: List<com.example.mbsarfachruddin.model.remote.blog.AuthorX>,
    @SerializedName("collection")
    val collection: List<com.example.mbsarfachruddin.model.remote.blog.Collection>,
    @SerializedName("replies")
    val replies: List<com.example.mbsarfachruddin.model.remote.blog.Reply>,
    @SerializedName("self")
    val self: List<com.example.mbsarfachruddin.model.remote.blog.Self>
)