package com.example.trempelapp.utils

import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.domain_layer.dto.LoginErrorDto

class ErrorUtils {

    companion object {
        fun checkUserCredentials(userCredentials: UserCredentials): LoginErrorDto {
            var loginErrorText: String = EMPTY_STRING
            var passwordErrorText: String = EMPTY_STRING

            if (userCredentials.login.isEmpty()) {
                loginErrorText = "Login cannot be empty"
            } else if (userCredentials.login.length < 3) {
                loginErrorText = "Login should be more than three characters"
            } else if (userCredentials.login.contains(" ")) {
                loginErrorText = "Login shouldn't contain spaces"
            }
            if (userCredentials.password.isEmpty()) {
                passwordErrorText = "Password cannot be empty"
            } else if (userCredentials.password.length < 3) {
                passwordErrorText = "Password should be more than three characters"
            } else if (userCredentials.password.contains(" ")) {
                passwordErrorText = "Password shouldn't contain spaces"
            }

            return LoginErrorDto(
                loginErrorText,
                passwordErrorText
            )
        }
    }
}
