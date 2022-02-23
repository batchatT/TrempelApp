package com.example.domain_layer.coroutine_use_cases

import com.example.domain_layer.repositories.UserInfoRepository
import javax.inject.Inject

class DeleteAllDataFromSharedPreferencesUseCaseImpl @Inject constructor(
    private val repository: UserInfoRepository
) : UseCase<Nothing?, Unit> {
    override suspend fun execute(params: Nothing?) {
        repository.clearUserData()
    }
}
