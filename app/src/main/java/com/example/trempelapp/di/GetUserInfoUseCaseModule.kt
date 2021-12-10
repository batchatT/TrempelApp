package com.example.trempelapp.di

import com.example.trempelapp.domainLayer.GetUserInfoUseCase
import com.example.trempelapp.domainLayer.GetUserInfoUseCaseImpl
import dagger.Binds
import dagger.Module


@Module
abstract class GetUserInfoUseCaseModule {

    @Binds
    abstract fun provideGetUserInfoUseCase(useCase: GetUserInfoUseCaseImpl): GetUserInfoUseCase

}
