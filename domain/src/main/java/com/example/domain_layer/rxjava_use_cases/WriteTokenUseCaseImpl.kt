package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.repositories.AuthRepository
import javax.inject.Inject

class WriteTokenUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<String, Unit> {

    override fun execute(params: String) {
        authRepository.saveUserToken(params)
    }
}
