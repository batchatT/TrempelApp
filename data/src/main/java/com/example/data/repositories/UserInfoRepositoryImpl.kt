package com.example.data.repositories

import com.example.data.shared_preferences.SharedPreferencesManager
import com.example.domain_layer.repositories.UserInfoRepository
import io.reactivex.Single
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferencesManager,
) : UserInfoRepository {

    override fun getLoginStatusByToken(): Single<String> {
        return preferences.getUserLoginStatus()
    }

    override suspend fun clearUserData() {
        preferences.logOut()
    }
}
