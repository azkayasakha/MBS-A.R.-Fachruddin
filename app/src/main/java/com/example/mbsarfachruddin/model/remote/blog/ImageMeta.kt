package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class ImageMeta(
    @SerializedName("aperture")
    val aperture: String,
    @SerializedName("camera")
    val camera: String,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("created_timestamp")
    val createdTimestamp: String,
    @SerializedName("credit")
    val credit: String,
    @SerializedName("focal_length")
    val focalLength: String,
    @SerializedName("iso")
    val iso: String,
    @SerializedName("keywords")
    val keywords: List<Any>,
    @SerializedName("orientation")
    val orientation: String,
    @SerializedName("shutter_speed")
    val shutterSpeed: String,
    @SerializedName("title")
    val title: String
)