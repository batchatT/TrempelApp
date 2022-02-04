package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.repositories.AuthRepository
import javax.inject.Inject

class WriteTokenUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<String, Unit> {

    override fun execute(params: String) {
        authRepository.saveUserToken(params)
    }
}
