package com.example.domain_layer.models

import com.google.gson.annotations.SerializedName

data class UserCredentials(

    @SerializedName("login")
    val login: String,

    @SerializedName("password")
    val password: String
)
