package com.example.trempelapp.di

import com.example.trempelapp.data_layer.AuthRepository
import com.example.trempelapp.data_layer.AuthRepositoryImpl
import com.example.trempelapp.data_layer.UserInfoRepository
import com.example.trempelapp.data_layer.UserInfoRepositoryImpl
import com.example.trempelapp.data_layer.inMemory.SharedPreferencesManager
import com.example.trempelapp.data_layer.inMemory.SharedPreferencesManagerImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserInfoRepository(repository: UserInfoRepositoryImpl): UserInfoRepository

    @Binds
    abstract fun provideAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideSharedPreferences(sharedPreferencesManagerImpl: SharedPreferencesManagerImpl): SharedPreferencesManager
}
