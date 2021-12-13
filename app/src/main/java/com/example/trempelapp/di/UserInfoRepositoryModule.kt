package com.example.trempelapp.di

import com.example.trempelapp.data_layer.UserInfoRepositoryImpl
import com.example.trempelapp.data_layer.UserInfoRepository
import dagger.Binds
import dagger.Module


@Module
abstract class UserInfoRepositoryModule {

    @Binds
    abstract fun provideUserInfoRepository(repository: UserInfoRepositoryImpl): UserInfoRepository

}
