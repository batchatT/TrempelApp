package com.example.trempelapp.data_layer

import android.content.SharedPreferences
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import com.example.trempelapp.data_layer.network.UserService
import io.reactivex.Single
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferences,
    private val userService: UserService
) : UserInfoRepository {

    override fun getLoginStatusByToken() {
        TODO()
    }

    override fun loginUser(): Single<LoginResponse> {
        return userService.loginUser(UserCredentials("dsa","asd"))
    }

}
