package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class BlogResponseItem(
    @SerializedName("author")
    val author: Int,
    @SerializedName("categories")
    val categories: List<Int>,
    @SerializedName("class_list")
    val classList: List<String>,
    @SerializedName("comment_status")
    val commentStatus: String,
    @SerializedName("content")
    val content: com.example.mbsarfachruddin.model.remote.blog.Content,
    @SerializedName("date")
    val date: String,
    @SerializedName("date_gmt")
    val dateGmt: String,
    @SerializedName("_embedded")
    val embedded: com.example.mbsarfachruddin.model.remote.blog.Embedded,
    @SerializedName("excerpt")
    val excerpt: com.example.mbsarfachruddin.model.remote.blog.Excerpt,
    @SerializedName("featured_media")
    val featuredMedia: Int,
    @SerializedName("format")
    val format: String,
    @SerializedName("guid")
    val guid: com.example.mbsarfachruddin.model.remote.blog.Guid,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("_links")
    val links: com.example.mbsarfachruddin.model.remote.blog.LinksXX,
    @SerializedName("meta")
    val meta: com.example.mbsarfachruddin.model.remote.blog.Meta,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("modified_gmt")
    val modifiedGmt: String,
    @SerializedName("ping_status")
    val pingStatus: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("sticky")
    val sticky: Boolean,
    @SerializedName("tags")
    val tags: List<Int>,
    @SerializedName("template")
    val template: String,
    @SerializedName("title")
    val title: com.example.mbsarfachruddin.model.remote.blog.TitleX,
    @SerializedName("type")
    val type: String
)