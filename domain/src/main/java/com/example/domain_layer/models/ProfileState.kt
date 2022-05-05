package com.example.domain_layer.models

data class ProfileState(
    val user: MainUserInfo? = null,
    val location: UserLocation? = null,
    val isLoading: Boolean = false
)
