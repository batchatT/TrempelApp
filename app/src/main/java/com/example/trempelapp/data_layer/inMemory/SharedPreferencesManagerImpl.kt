package com.example.trempelapp.data_layer.inMemory

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.trempelapp.utils.TOKEN_PREFERENCES_FILE
import io.reactivex.Single
import javax.inject.Inject

class SharedPreferencesManagerImpl @Inject constructor(context: Context) :
    SharedPreferencesManager {

    private val tokenKey = ""

    private val preferences: SharedPreferences =
        context.getSharedPreferences(TOKEN_PREFERENCES_FILE, Context.MODE_PRIVATE)

    override fun getUserLoginStatus(): Single<String> {
        val token = preferences.getString(tokenKey, "")
        Log.d("Share", "Token was given")
        return Single.just(token)
    }
}
