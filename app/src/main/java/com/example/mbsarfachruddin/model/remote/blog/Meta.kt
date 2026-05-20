package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("footnotes")
    val footnotes: String,
    @SerializedName("ngg_post_thumbnail")
    val nggPostThumbnail: Int
)