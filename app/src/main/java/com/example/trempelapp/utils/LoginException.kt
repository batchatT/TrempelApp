package com.example.trempelapp.utils

import java.lang.Exception

class LoginException(
    message: String,
    val loginErrorText: String? = null,
    val passwordErrorText: String? = null,
) : Exception(message)
