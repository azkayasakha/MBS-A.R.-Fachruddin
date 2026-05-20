package com.example.mbsarfachruddin.model.remote.musyrif.wallet.update


import com.google.gson.annotations.SerializedName

data class WalletUpdateResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)