package com.example.domain_layer.coroutine_use_cases

import android.graphics.Bitmap
import android.net.Uri
import com.example.domain_layer.repositories.UserInfoRepository
import javax.inject.Inject

class SaveUserImageUseCaseImpl @Inject constructor(
    private val repository: UserInfoRepository
) : UseCase<Bitmap, Uri> {

    override suspend fun execute(params: Bitmap): Uri {
        return repository.saveUserImage(params)
    }
}
