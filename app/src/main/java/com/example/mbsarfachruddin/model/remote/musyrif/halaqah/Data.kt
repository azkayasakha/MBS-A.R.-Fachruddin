package com.example.mbsarfachruddin.model.remote.musyrif.halaqah


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("halaqah_id")
    val halaqahId: Int,
    @SerializedName("musyrif_id")
    val musyrifId: String,
    @SerializedName("name")
    val name: String
)