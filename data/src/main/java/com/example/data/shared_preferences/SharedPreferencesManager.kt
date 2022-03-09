package com.example.data.shared_preferences

import io.reactivex.Single

interface SharedPreferencesManager {

    fun getUserLoginStatus(): Single<String>

    fun writeToken(token: String)

    suspend fun logOut()
}
