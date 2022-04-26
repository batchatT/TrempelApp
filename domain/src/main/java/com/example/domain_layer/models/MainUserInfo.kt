package com.example.domain_layer.models

import android.net.Uri

data class MainUserInfo(
    val email: String,
    val username: String,
    val phone: String,
    val name: String,
    val address: String,
    var imageUri: Uri? = null
)
