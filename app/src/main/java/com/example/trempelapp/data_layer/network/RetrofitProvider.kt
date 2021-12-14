package com.example.trempelapp.data_layer.network

interface RetrofitProvider {

    fun <T> provideService(service: Class<T>): T
}
