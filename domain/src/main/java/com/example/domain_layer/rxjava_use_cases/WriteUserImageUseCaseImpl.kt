package com.example.domain_layer.rxjava_use_cases

import android.net.Uri
import com.example.domain_layer.repositories.UserInfoRepository
import io.reactivex.Completable
import javax.inject.Inject

class WriteUserImageUseCaseImpl @Inject constructor(
    private val repository: UserInfoRepository
) : UseCase<Uri, Completable> {

    override fun execute(params: Uri): Completable {
        return repository.writeUserImageUri(params)
    }
}
