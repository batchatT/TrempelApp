package com.example.trempelapp.domain_layer.coroutine

interface UseCase<in P, out T> {

    suspend fun execute(params: P): T
}

suspend fun <T> UseCase<Nothing?, T>.execute() = execute(null)
