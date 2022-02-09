package com.example.trempelapp.data_layer.repositories

import com.example.trempelapp.data_layer.in_memory.shared_preferences.SharedPreferencesManager
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.login.LoginResponse
import com.example.trempelapp.data_layer.network.login.UserService
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