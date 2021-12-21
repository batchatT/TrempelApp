package com.example.trempelapp.data_layer

import com.example.trempelapp.data_layer.inMemory.SharedPreferencesManager
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import com.example.trempelapp.data_layer.network.UserService
import io.reactivex.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userService: UserService,
) : AuthRepository {

    override fun saveUserToken(token: String) {
        sharedPreferencesManager.writeToken(token)
    }

    override fun logInUser(userCredentials: UserCredentials): Single<LoginResponse> {
        return userService.loginUser(userCredentials.login, userCredentials.password)
    }
}
