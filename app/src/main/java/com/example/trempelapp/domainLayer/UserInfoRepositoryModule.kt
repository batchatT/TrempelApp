package com.example.trempelapp.domainLayer

import com.example.trempelapp.dataLayer.inMemory.UserInfoRepositoryImpl
import com.example.trempelapp.dataLayer.inMemory.UserInfoRepository
import dagger.Binds
import dagger.Module


@Module
abstract class UserInfoRepositoryModule {

    @Binds
    abstract fun provideUserInfoRepository(repository: UserInfoRepositoryImpl): UserInfoRepository

}
