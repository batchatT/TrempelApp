package com.example.domain_layer.repositories

import io.reactivex.Single

interface UserInfoRepository {

    fun getLoginStatusByToken(): Single<String>

    suspend fun clearUserData()
}
