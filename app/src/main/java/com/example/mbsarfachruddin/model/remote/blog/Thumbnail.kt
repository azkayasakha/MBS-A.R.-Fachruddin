package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("file")
    val `file`: String,
    @SerializedName("filesize")
    val filesize: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("mime_type")
    val mimeType: String,
    @SerializedName("source_url")
    val sourceUrl: String,
    @SerializedName("width")
    val width: Int
)