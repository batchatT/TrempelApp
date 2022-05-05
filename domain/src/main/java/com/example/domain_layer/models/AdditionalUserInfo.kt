package com.example.domain_layer.models

import android.net.Uri

data class AdditionalUserInfo(
    val login: String,
    val imageUri: Uri? = null
)
