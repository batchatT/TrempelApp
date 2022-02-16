package com.example.trempelapp.domain_layer.coroutine

import com.example.trempelapp.data_layer.repositories.UserInfoRepository
import javax.inject.Inject

class DeleteAllDataFromSharedPreferencesUseCaseImpl @Inject constructor(
    private val repository: UserInfoRepository
) : UseCase<Nothing?, Unit> {
    override suspend fun execute(params: Nothing?) {
        repository.clearUserData()
    }
}
