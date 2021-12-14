package com.example.trempelapp.di

import com.example.trempelapp.data_layer.UserInfoRepository
import com.example.trempelapp.data_layer.UserInfoRepositoryImpl
import com.example.trempelapp.data_layer.inMemory.SharedPreferencesManager
import com.example.trempelapp.data_layer.inMemory.SharedPreferencesManagerImpl
import com.example.trempelapp.data_layer.network.UserService
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideUserInfoRepository(
        sharedPreferencesManager: SharedPreferencesManager,
        userService: UserService,
    ): UserInfoRepository {
        return UserInfoRepositoryImpl(sharedPreferencesManager, userService)
    }

    @Provides
    fun provideSharedPreferences(): SharedPreferencesManager {
        return SharedPreferencesManagerImpl()
    }
}
