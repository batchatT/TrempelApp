package com.example.trempelapp.domain_layer.coroutine

import com.example.trempelapp.data_layer.repositories.CartRepository
import javax.inject.Inject

class LogOutUseCaseImpl @Inject constructor(
    private val repository: CartRepository

) : UseCase<Nothing?, Unit> {

    override suspend fun execute(params: Nothing?) {
        TODO("Not yet implemented")
    }
}
