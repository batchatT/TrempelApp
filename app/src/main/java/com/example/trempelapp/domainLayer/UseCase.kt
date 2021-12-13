package com.example.trempelapp.domainLayer

import com.example.trempelapp.data_layer.network.LoginResponse
import io.reactivex.Single

interface UseCase<in P, out T> {

    fun execute(params: P): T
}

fun <T> UseCase<Nothing?, T>.execute() = execute(null)
