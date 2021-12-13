package com.example.trempelapp.di

import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import com.example.trempelapp.domainLayer.AuthUseCaseImpl
import com.example.trempelapp.domainLayer.UseCase
import dagger.Binds
import dagger.Module
import io.reactivex.Single


@Module
abstract class GetUserInfoUseCaseModule {

    @Binds
    abstract fun provideGetUserInfoUseCase(useCase: AuthUseCaseImpl): UseCase<UserCredentials, Single<LoginResponse>>

}
