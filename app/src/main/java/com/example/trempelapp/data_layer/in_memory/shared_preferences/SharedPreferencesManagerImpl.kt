package com.example.trempelapp.data_layer.in_memory.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.trempelapp.utils.EMPTY_STRING
import com.example.trempelapp.utils.TOKEN_PREFERENCES_FILE
import io.reactivex.Single
import javax.inject.Inject

private const val TOKEN = "token"

class SharedPreferencesManagerImpl @Inject constructor(
    context: Context,
) : SharedPreferencesManager {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(TOKEN_PREFERENCES_FILE, Context.MODE_PRIVATE)

    override fun getUserLoginStatus(): Single<String> {
        val token = preferences.getString(TOKEN, EMPTY_STRING)
        return Single.just(token)
    }

    override fun writeToken(token: String) {
        val editor = preferences.edit()
        editor
            .putString(TOKEN, token)
            .apply()
    }

    override suspend fun logOut() {
        val editor = preferences.edit()
        editor
            .clear()
            .apply()
    }
}
