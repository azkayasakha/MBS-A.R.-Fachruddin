package com.example.mbsarfachruddin.model.remote.musyrif.halaqahmember


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("name")
    val name: String,
    @SerializedName("nisn")
    val nisn: String
)