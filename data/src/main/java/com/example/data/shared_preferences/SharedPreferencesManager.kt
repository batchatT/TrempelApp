package com.example.data.shared_preferences

import com.example.domain_layer.models.AdditionalUserInfo
import io.reactivex.Single

interface SharedPreferencesManager {

    fun getUserLoginStatus(): Single<String>

    fun getAdditionalUserInfo(): Single<AdditionalUserInfo>

    fun writeToken(token: String)

    fun writeUserLogin(login: String)

    fun writeUserImage(image: String)

    suspend fun logOut()
}
