package com.example.data.repositories

import com.example.data.services.UserService
import com.example.data.shared_preferences.SharedPreferencesManager
import com.example.domain_layer.models.LoginResponse
import com.example.domain_layer.models.UserCredentials
import com.example.domain_layer.repositories.AuthRepository
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

    override suspend fun logOutUser() {
        sharedPreferencesManager.logOut()
    }
}
