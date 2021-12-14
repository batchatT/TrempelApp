package com.example.trempelapp.di

import com.example.trempelapp.data_layer.network.Retrofit
import com.example.trempelapp.data_layer.network.UserService
import dagger.Module
import dagger.Provides


@Module
class RetrofitModule {

    @Provides
    fun provideNetwork(retrofit: Retrofit): UserService {
        return retrofit.provideService(UserService::class.java)
    }

}
