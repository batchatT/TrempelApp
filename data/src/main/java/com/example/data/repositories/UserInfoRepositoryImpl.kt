package com.example.data.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.example.data.internal_storage.InternalStorageManager
import com.example.data.location.UserLocationManager
import com.example.data.network.user.UserService
import com.example.data.shared_preferences.SharedPreferencesManager
import com.example.domain_layer.models.AdditionalUserInfo
import com.example.domain_layer.models.UserInfo
import com.example.domain_layer.repositories.UserInfoRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferencesManager,
    private val userService: UserService,
    private val internalStorage: InternalStorageManager,
    private val locationManager: UserLocationManager
) : UserInfoRepository {

    override fun getLoginStatusByToken(): Single<String> {
        return preferences.getUserLoginStatus()
    }

    override fun getAllUsersInfo(): Single<List<UserInfo>> {
        return userService.getAllUsers()
    }

    override fun getAdditionalUserInfo(): Single<AdditionalUserInfo> {
        return preferences.getAdditionalUserInfo()
    }

    override fun writeUserImageUri(uri: Uri): Completable {
        return Completable.fromAction {
            preferences.writeUserImage(uri.toString())
        }
    }

    override suspend fun isGpsEnabled(): Boolean {
        return locationManager.isGpsEnabled()
    }

    override suspend fun saveUserImage(image: Bitmap): Uri {
        return internalStorage.saveUserImage(image)
    }

    override suspend fun clearUserData() {
        preferences.logOut()
    }
}
