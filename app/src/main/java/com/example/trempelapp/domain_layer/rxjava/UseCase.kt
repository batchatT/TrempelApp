package com.example.trempelapp.domain_layer.rxjava

interface UseCase<in P, out T> {

    fun execute(params: P): T
}

fun <T> UseCase<Nothing?, T>.execute() = execute(null)
