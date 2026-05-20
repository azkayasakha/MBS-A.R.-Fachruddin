package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class LinksXX(
    @SerializedName("about")
    val about: List<com.example.mbsarfachruddin.model.remote.blog.About>,
    @SerializedName("author")
    val author: List<com.example.mbsarfachruddin.model.remote.blog.AuthorX>,
    @SerializedName("collection")
    val collection: List<com.example.mbsarfachruddin.model.remote.blog.Collection>,
    @SerializedName("curies")
    val curies: List<com.example.mbsarfachruddin.model.remote.blog.Cury>,
    @SerializedName("predecessor-version")
    val predecessorVersion: List<com.example.mbsarfachruddin.model.remote.blog.PredecessorVersion>,
    @SerializedName("replies")
    val replies: List<com.example.mbsarfachruddin.model.remote.blog.Reply>,
    @SerializedName("self")
    val self: List<com.example.mbsarfachruddin.model.remote.blog.Self>,
    @SerializedName("version-history")
    val versionHistory: List<com.example.mbsarfachruddin.model.remote.blog.VersionHistory>,
    @SerializedName("wp:attachment")
    val wpAttachment: List<com.example.mbsarfachruddin.model.remote.blog.WpAttachment>,
    @SerializedName("wp:featuredmedia")
    val wpFeaturedmedia: List<com.example.mbsarfachruddin.model.remote.blog.WpFeaturedmediaX>,
    @SerializedName("wp:term")
    val wpTerm: List<com.example.mbsarfachruddin.model.remote.blog.WpTermX>
)