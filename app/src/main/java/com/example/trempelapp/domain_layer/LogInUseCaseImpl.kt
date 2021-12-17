package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.AuthRepository
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import io.reactivex.Single
import javax.inject.Inject

class LogInUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : UseCase<UserCredentials, Single<String>> {

    override fun execute(params: UserCredentials): Single<String> = repository
        .logInUser(params)
        .map{ it.token }
}
