package com.example.domain_layer

import com.example.domain_layer.dto.LoginErrorDto
import com.example.domain_layer.dto.SearchErrorDto
import com.example.domain_layer.models.UserCredentials
import com.example.trempelapp.utils.EMPTY_STRING

class ErrorUtils {

    companion object {
        fun checkSearchQuery(query: String): SearchErrorDto {
            var searchQueryErrorText: String = EMPTY_STRING

            when {
                query.isEmpty() -> {
                    searchQueryErrorText = "Field cannot be empty"
                }
                query.length < 3 -> {
                    searchQueryErrorText = "Query should be more than three characters"
                }
            }
            return SearchErrorDto(
                searchQueryErrorText
            )
        }

        fun checkUserCredentials(userCredentials: UserCredentials): LoginErrorDto {
            var loginErrorText: String = EMPTY_STRING
            var passwordErrorText: String = EMPTY_STRING

            when {
                userCredentials.login.isEmpty() -> {
                    loginErrorText = "Login cannot be empty"
                }
                userCredentials.login.length < 3 -> {
                    loginErrorText = "Login should be more than three characters"
                }
                userCredentials.login.contains(" ") -> {
                    loginErrorText = "Login shouldn't contain spaces"
                }
            }

            when {
                userCredentials.password.isEmpty() -> {
                    passwordErrorText = "Password cannot be empty"
                }
                userCredentials.password.length < 3 -> {
                    passwordErrorText = "Password should be more than three characters"
                }
                userCredentials.password.contains(" ") -> {
                    passwordErrorText = "Password shouldn't contain spaces"
                }
            }

            return LoginErrorDto(
                loginErrorText,
                passwordErrorText
            )
        }
    }
}
