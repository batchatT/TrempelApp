package com.example.domain_layer.rxjava_use_cases

import android.net.Uri
import com.example.domain_layer.models.MainUserInfo
import com.example.domain_layer.repositories.UserInfoRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUserDataUseCaseImpl @Inject constructor(
    private val repository: UserInfoRepository
) : UseCase<Nothing?, Single<MainUserInfo>> {

    override fun execute(params: Nothing?): Single<MainUserInfo> {
        var imageUri: Uri? = null
        return Single.zip(
            repository.getAllUsersInfo(),
            repository.getAdditionalUserInfo()
        ) { allUsers, currentUser ->
            imageUri = currentUser.imageUri
            allUsers.first {
                it.username == currentUser.login
            }
        }
            .map {
                MainUserInfo(
                    email = it.email,
                    username = it.username,
                    phone = it.phone,
                    name = it.fullName,
                    address = it.fullAddress,
                    imageUri = imageUri
                )
            }
    }
}
