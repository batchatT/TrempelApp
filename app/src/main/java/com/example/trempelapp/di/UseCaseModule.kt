package com.example.trempelapp.di

import com.example.trempelapp.data_layer.UserInfoRepository
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import com.example.trempelapp.domain_layer.AuthUseCaseImpl
import com.example.trempelapp.domain_layer.UseCase
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import javax.inject.Named

@Module
class UseCaseModule {

    @Named("authUseCaseImpl")
    @Provides
    fun provideGetUserInfoUseCase(repository: UserInfoRepository): UseCase<UserCredentials, Single<LoginResponse>> {
        return AuthUseCaseImpl(repository)
    }
}
