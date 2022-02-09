package com.example.trempelapp.data_layer.repositories

import io.reactivex.Single

interface UserInfoRepository {

    fun getLoginStatusByToken(): Single<String>
}
