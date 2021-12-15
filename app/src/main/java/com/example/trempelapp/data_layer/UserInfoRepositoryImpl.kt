package com.example.trempelapp.data_layer

import com.example.trempelapp.data_layer.inMemory.SharedPreferencesManager
import com.example.trempelapp.data_layer.network.UserService
import io.reactivex.Single
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferencesManager,
    private val userService: UserService
) : UserInfoRepository {

    override fun getLoginStatusByToken(): Single<String> {
        return preferences.getUserLoginStatus()
    }

//    override fun loginUser(): Single<LoginResponse> {
//        return userService.loginUser(UserCredentials("dsa", "asd"))
//    }
}
