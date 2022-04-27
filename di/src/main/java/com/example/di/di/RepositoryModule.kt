package com.example.di.di

import com.example.data.internal_storage.InternalStorageManager
import com.example.data.internal_storage.InternalStorageManagerImpl
import com.example.data.repositories.AuthRepositoryImpl
import com.example.data.repositories.CartRepositoryImpl
import com.example.data.repositories.FavouritesRepositoryImpl
import com.example.data.repositories.RecentlyProductsRepositoryImpl
import com.example.data.repositories.UserInfoRepositoryImpl
import com.example.data.shared_preferences.SharedPreferencesManager
import com.example.data.shared_preferences.SharedPreferencesManagerImpl
import com.example.domain_layer.repositories.AuthRepository
import com.example.domain_layer.repositories.CartRepository
import com.example.domain_layer.repositories.FavouritesRepository
import com.example.domain_layer.repositories.RecentlyProductsRepository
import com.example.domain_layer.repositories.UserInfoRepository
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

    @Binds
    abstract fun provideCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun provideInternalStorageManager(internalStorageManagerImpl: InternalStorageManagerImpl): InternalStorageManager
}
