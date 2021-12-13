package com.example.trempelapp.domainLayer

import com.example.trempelapp.data_layer.UserInfoRepository
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import io.reactivex.Single
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
     private val repository: UserInfoRepository,
): UseCase<UserCredentials, Single<LoginResponse>>{

     override fun execute(params: UserCredentials): Single<LoginResponse> = repository.loginUser()

}
