package com.example.mbsarfachruddin.model.remote.musyrif.profile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String,
    @SerializedName("card_id")
    val cardId: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("musyrif_id")
    val musyrifId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    @SerializedName("telephone")
    val telephone: String
)