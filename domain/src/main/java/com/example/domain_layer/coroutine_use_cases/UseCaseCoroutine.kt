package com.example.domain_layer.coroutine_use_cases

interface UseCase<in P, out T> {

    suspend fun execute(params: P): T
}

suspend fun <T> UseCase<Nothing?, T>.execute() = execute(null)
