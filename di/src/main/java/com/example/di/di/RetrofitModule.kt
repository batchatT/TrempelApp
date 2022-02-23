package com.example.di.di

import com.example.data.network.Retrofit
import com.example.data.services.UserService
import dagger.Module
import dagger.Provides

@Module
class RetrofitModule {

    @Provides
    fun provideNetwork(retrofit: Retrofit): UserService {
        return retrofit.provideService(UserService::class.java)
    }
}
