package com.example.mbsarfachruddin.model.remote.musyrif.wallet


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("balance")
    val balance: Int
)