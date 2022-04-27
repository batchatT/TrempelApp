package com.example.domain_layer.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.example.domain_layer.models.AdditionalUserInfo
import com.example.domain_layer.models.UserInfo
import io.reactivex.Completable
import io.reactivex.Single

interface UserInfoRepository {

    fun getLoginStatusByToken(): Single<String>

    fun getAllUsersInfo(): Single<List<UserInfo>>

    fun getAdditionalUserInfo(): Single<AdditionalUserInfo>

    fun writeUserImageUri(uri: Uri): Completable

    suspend fun saveUserImage(image: Bitmap): Uri

    suspend fun clearUserData()
}
