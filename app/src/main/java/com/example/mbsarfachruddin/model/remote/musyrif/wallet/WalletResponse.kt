package com.example.mbsarfachruddin.model.remote.musyrif.wallet


import com.google.gson.annotations.SerializedName

data class WalletResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)