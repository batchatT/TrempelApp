package com.example.domain_layer.rxjava_use_cases

interface UseCase<in P, out T> {

    fun execute(params: P): T
}

fun <T> UseCase<Nothing?, T>.execute() = execute(null)
