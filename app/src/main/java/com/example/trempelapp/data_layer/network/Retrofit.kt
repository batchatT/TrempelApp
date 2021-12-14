package com.example.trempelapp.data_layer.network

import com.example.trempelapp.BASE_URL
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class Retrofit @Inject constructor() : RetrofitProvider {

    override fun <T> provideService(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(service)
    }
}
