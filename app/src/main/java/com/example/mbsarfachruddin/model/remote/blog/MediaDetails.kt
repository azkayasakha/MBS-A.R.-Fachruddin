package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class MediaDetails(
    @SerializedName("file")
    val `file`: String,
    @SerializedName("filesize")
    val filesize: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("image_meta")
    val imageMeta: com.example.mbsarfachruddin.model.remote.blog.ImageMeta,
    @SerializedName("sizes")
    val sizes: com.example.mbsarfachruddin.model.remote.blog.Sizes,
    @SerializedName("width")
    val width: Int
)