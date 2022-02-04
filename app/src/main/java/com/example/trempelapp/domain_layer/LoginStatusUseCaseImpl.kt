package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.repositories.UserInfoRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginStatusUseCaseImpl @Inject constructor(
    private val repository: UserInfoRepository,
) : UseCase<Nothing?, Single<Boolean>> {

    override fun execute(params: Nothing?): Single<Boolean> = repository
        .getLoginStatusByToken()
        .map { it.isNotEmpty() }
}
