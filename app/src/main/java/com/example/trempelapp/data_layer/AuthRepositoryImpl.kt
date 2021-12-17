package com.example.trempelapp.data_layer

import com.example.trempelapp.data_layer.inMemory.SharedPreferencesManager
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import com.example.trempelapp.data_layer.network.UserService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.security.auth.callback.Callback

class AuthRepositoryImpl @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userService: UserService,
) : AuthRepository {

    override fun writeTokenToSharedPreference(token: String) {
        sharedPreferencesManager.writeToken(token)
    }

    override fun logInUser(userCredentials: UserCredentials): Single<LoginResponse> {
        return userService.loginUser(userCredentials.login, userCredentials.password)
    }
}
