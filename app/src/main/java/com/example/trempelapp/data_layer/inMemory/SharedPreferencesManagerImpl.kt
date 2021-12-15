package com.example.trempelapp.data_layer.inMemory

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.trempelapp.utils.EMPTY_STRING
import com.example.trempelapp.utils.TOKEN_PREFERENCES_FILE
import io.reactivex.Single
import javax.inject.Inject

private const val TOKEN = "token"

class SharedPreferencesManagerImpl @Inject constructor(
    context: Context,
): SharedPreferencesManager {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(TOKEN_PREFERENCES_FILE, Context.MODE_PRIVATE)

    override fun getUserLoginStatus(): Single<String> {
        val token = preferences.getString(TOKEN, EMPTY_STRING)
        Log.d("Share", "Token was given")
        return Single.just(token)
    }
}
