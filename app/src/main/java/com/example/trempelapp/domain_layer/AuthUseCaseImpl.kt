package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import io.reactivex.Single
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    // some repository
) : UseCase<UserCredentials, Single<LoginResponse>> {

    override fun execute(params: UserCredentials): Single<LoginResponse> = TODO()
}
