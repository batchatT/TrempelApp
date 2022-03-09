package com.example.domain_layer.dto

class SearchErrorDto(
    val queryError: String? = null
) {
    fun isSearchQueryCorrect(): Boolean {
        return queryError.isNullOrEmpty()
    }
}
