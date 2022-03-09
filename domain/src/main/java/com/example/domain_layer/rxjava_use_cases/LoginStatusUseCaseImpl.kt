package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.repositories.UserInfoRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginStatusUseCaseImpl @Inject constructor(
    private val repository: UserInfoRepository,
) : UseCase<Nothing?, Single<Boolean>> {

    override fun execute(params: Nothing?): Single<Boolean> = repository
        .getLoginStatusByToken()
        .map { it.isNotEmpty() }
}
