package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.AuthRepository
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.utils.ErrorUtils
import com.example.trempelapp.utils.LoginException
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
