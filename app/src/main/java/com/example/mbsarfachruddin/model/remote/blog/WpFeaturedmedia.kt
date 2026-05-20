package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class WpFeaturedmedia(
    @SerializedName("alt_text")
    val altText: String,
    @SerializedName("author")
    val author: Int,
    @SerializedName("caption")
    val caption: com.example.mbsarfachruddin.model.remote.blog.Caption,
    @SerializedName("date")
    val date: String,
    @SerializedName("featured_media")
    val featuredMedia: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("_links")
    val links: com.example.mbsarfachruddin.model.remote.blog.Links,
    @SerializedName("media_details")
    val mediaDetails: com.example.mbsarfachruddin.model.remote.blog.MediaDetails,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("mime_type")
    val mimeType: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("source_url")
    val sourceUrl: String,
    @SerializedName("title")
    val title: com.example.mbsarfachruddin.model.remote.blog.TitleX,
    @SerializedName("type")
    val type: String
)