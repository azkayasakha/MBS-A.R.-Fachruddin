package com.example.mbsarfachruddin.model.remote.blog


import com.google.gson.annotations.SerializedName

data class TargetHints(
    @SerializedName("allow")
    val allow: List<String>
)