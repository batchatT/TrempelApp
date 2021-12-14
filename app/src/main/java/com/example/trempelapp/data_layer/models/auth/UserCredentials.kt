package com.example.trempelapp.data_layer.models.auth

import com.google.gson.annotations.SerializedName

data class UserCredentials(

    @SerializedName("login")
    val login: String,

    @SerializedName("password")
    val password: String
)
