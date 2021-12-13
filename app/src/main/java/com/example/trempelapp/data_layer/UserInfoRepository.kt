package com.example.trempelapp.data_layer

import com.example.trempelapp.data_layer.network.LoginResponse
import io.reactivex.Single

interface UserInfoRepository {

    fun getLoginStatusByToken()

    fun loginUser(): Single<LoginResponse>

}
