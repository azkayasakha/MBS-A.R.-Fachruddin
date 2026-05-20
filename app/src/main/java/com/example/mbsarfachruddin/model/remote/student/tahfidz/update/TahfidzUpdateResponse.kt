package com.example.mbsarfachruddin.model.remote.student.tahfidz.update

import com.google.gson.annotations.SerializedName

data class TahfidzUpdateResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)