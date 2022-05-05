package com.example.domain_layer.coroutine_use_cases

import com.example.domain_layer.repositories.UserInfoRepository
import javax.inject.Inject

class GetGpsStatusUseCaseImpl @Inject constructor(
    private val repository: UserInfoRepository
) : UseCase<Nothing?, Boolean> {

    override suspend fun execute(params: Nothing?): Boolean {
        return repository.isGpsEnabled()
    }
}
