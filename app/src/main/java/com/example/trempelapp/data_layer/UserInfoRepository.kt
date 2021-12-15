package com.example.trempelapp.data_layer

import io.reactivex.Single

interface UserInfoRepository {

    fun getLoginStatusByToken(): Single<String>

//    fun loginUser(): Single<LoginResponse>
}
