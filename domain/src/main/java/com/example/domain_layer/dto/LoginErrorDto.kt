package com.example.domain_layer.dto

data class LoginErrorDto(
    val loginErrorText: String? = null,
    val passwordErrorText: String? = null,
) {

    fun isLoginCorrect(): Boolean {
        return loginErrorText.isNullOrEmpty() && passwordErrorText.isNullOrEmpty()
    }
}
