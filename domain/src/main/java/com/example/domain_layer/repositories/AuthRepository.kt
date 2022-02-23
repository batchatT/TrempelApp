package com.example.domain_layer.repositories

import com.example.domain_layer.models.LoginResponse
import com.example.domain_layer.models.UserCredentials
import io.reactivex.Single

interface AuthRepository {

    fun saveUserToken(token: String)

    fun logInUser(userCredentials: UserCredentials): Single<LoginResponse>

    suspend fun logOutUser()
}
