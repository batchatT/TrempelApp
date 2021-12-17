package com.example.trempelapp.data_layer

import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import io.reactivex.Single

interface AuthRepository {

    fun writeTokenToSharedPreference(token: String)

    fun logInUser(userCredentials: UserCredentials): Single<LoginResponse>

}
