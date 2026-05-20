package com.example.mbsarfachruddin.model.remote.student.transaction


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("balance")
    val balance: Int,
    @SerializedName("history")
    val history: List<History>
)