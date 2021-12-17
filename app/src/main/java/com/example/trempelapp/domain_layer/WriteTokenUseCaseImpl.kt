package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.AuthRepository
import javax.inject.Inject

class WriteTokenUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<String, String> {

    override fun execute(params: String): String {
        authRepository.writeTokenToSharedPreference(params)
        return ""
    }

}