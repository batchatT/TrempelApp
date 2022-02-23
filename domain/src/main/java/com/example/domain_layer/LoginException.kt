package com.example.domain_layer

import java.lang.Exception

class LoginException(
    message: String,
    val loginErrorText: String? = null,
    val passwordErrorText: String? = null,
) : Exception(message)
