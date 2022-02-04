package com.example.trempelapp.di

import com.example.trempelapp.data_layer.in_memory.shared_preferences.SharedPreferencesManager
import com.example.trempelapp.data_layer.in_memory.shared_preferences.SharedPreferencesManagerImpl
import com.example.trempelapp.data_layer.repositories.AuthRepositoryImpl
import com.example.trempelapp.data_layer.repositories.RecentlyProductsRepositoryImpl
import com.example.trempelapp.data_layer.repositories.UserInfoRepositoryImpl
import com.example.trempelapp.data_layer.repositories.FavouritesRepositoryImpl
import com.example.trempelapp.data_layer.repositories.UserInfoRepository
import com.example.trempelapp.data_layer.repositories.AuthRepository
import com.example.trempelapp.data_layer.repositories.RecentlyProductsRepository
import com.example.trempelapp.data_layer.repositories.FavouritesRepository
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

    @Binds
    abstract fun provideRecentlyProductsRepository(recentlyProductsRepositoryImpl: RecentlyProductsRepositoryImpl): RecentlyProductsRepository

    @Binds
    abstract fun provideFavouritesRepository(favouritesRepositoryImpl: FavouritesRepositoryImpl): FavouritesRepository
}
