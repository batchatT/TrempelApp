package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.ErrorUtils
import com.example.domain_layer.LoginException
import com.example.domain_layer.models.UserCredentials
import com.example.domain_layer.repositories.AuthRepository
import io.reactivex.Single
import javax.inject.Inject

class LogInUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : UseCase<UserCredentials, Single<String>> {

    override fun execute(params: UserCredentials): Single<String> {
        val loginErrorDto = ErrorUtils.checkUserCredentials(params)
        if (!loginErrorDto.isLoginCorrect()) {
            return Single.error(
                LoginException(
                    "Login is incorrect",
                    loginErrorDto.loginErrorText,
                    loginErrorDto.passwordErrorText,
                )
            )
        }
        return repository
            .logInUser(params)
            .map { it.token }
    }
}
