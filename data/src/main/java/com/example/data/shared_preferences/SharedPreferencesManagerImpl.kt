package com.example.data.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.net.toUri
import com.example.domain_layer.models.AdditionalUserInfo
import com.example.trempelapp.utils.EMPTY_STRING
import com.example.trempelapp.utils.TOKEN_PREFERENCES_FILE
import io.reactivex.Single
import javax.inject.Inject

private const val TOKEN = "token"
private const val LOGIN = "login"
private const val IMAGE = "image"

class SharedPreferencesManagerImpl @Inject constructor(
    context: Context,
) : SharedPreferencesManager {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(TOKEN_PREFERENCES_FILE, Context.MODE_PRIVATE)

    override fun getUserLoginStatus(): Single<String> {
        val token = preferences.getString(TOKEN, EMPTY_STRING)
        return Single.just(token)
    }

    override fun getAdditionalUserInfo(): Single<AdditionalUserInfo> {
        val data = AdditionalUserInfo(
            login = preferences.getString(LOGIN, EMPTY_STRING) ?: EMPTY_STRING,
            imageUri = preferences.getString(IMAGE, EMPTY_STRING)?.toUri() ?: EMPTY_STRING.toUri()
        )
        return Single.just(data)
    }

    override fun writeToken(token: String) {
        val editor = preferences.edit()
        editor
            .putString(TOKEN, token)
            .apply()
    }

    override fun writeUserLogin(login: String) {
        val editor = preferences.edit()
        editor
            .putString(LOGIN, login)
            .apply()
    }

    override fun writeUserImage(image: String) {
        val editor = preferences.edit()
        editor
            .putString(IMAGE, image)
            .apply()
    }

    override suspend fun logOut() {
        val editor = preferences.edit()
        editor
            .clear()
            .apply()
    }
}
