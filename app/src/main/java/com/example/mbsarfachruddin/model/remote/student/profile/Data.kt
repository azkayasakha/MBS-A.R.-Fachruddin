package com.example.mbsarfachruddin.model.remote.student.profile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String,
    @SerializedName("card_id")
    val cardId: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("father_name")
    val fatherName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("mother_name")
    val motherName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nisn")
    val nisn: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    @SerializedName("telephone")
    val telephone: String
)