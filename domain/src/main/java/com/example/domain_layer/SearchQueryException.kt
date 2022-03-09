package com.example.domain_layer

import java.lang.Exception

class SearchQueryException(
    message: String,
    val queryError: String? = null
) : Exception(message)
