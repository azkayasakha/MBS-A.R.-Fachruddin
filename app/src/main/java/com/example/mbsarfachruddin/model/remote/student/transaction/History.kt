package com.example.mbsarfachruddin.model.remote.student.transaction


import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("balance")
    val balance: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("total")
    val total: Int
)