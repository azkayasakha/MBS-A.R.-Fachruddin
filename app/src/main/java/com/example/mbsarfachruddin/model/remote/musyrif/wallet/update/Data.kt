package com.example.mbsarfachruddin.model.remote.musyrif.wallet.update


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("new_balance")
    val newBalance: Int
)