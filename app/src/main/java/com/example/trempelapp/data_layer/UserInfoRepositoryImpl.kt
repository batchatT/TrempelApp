package com.example.trempelapp.data_layer

import com.example.trempelapp.data_layer.inMemory.SharedPreferencesManager
import io.reactivex.Single
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferencesManager,
) : UserInfoRepository {

    override fun getLoginStatusByToken(): Single<String> {
        return preferences.getUserLoginStatus()
    }
}
