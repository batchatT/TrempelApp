package com.example.data.network

interface RetrofitProvider {

    fun <T> provideService(service: Class<T>): T
}
