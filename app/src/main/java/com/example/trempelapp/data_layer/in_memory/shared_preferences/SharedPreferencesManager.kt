package com.example.trempelapp.data_layer.in_memory.shared_preferences

import io.reactivex.Single

interface SharedPreferencesManager {

    fun getUserLoginStatus(): Single<String>

    fun writeToken(token: String)
}
