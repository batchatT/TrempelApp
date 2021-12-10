package com.example.trempelapp.di

import com.example.trempelapp.dataLayer.inMemory.SharedPreferencesManager
import com.example.trempelapp.dataLayer.inMemory.SharedPreferencesManagerImpl
import dagger.Binds
import dagger.Module

@Module
abstract class SharedPreferencesManagerModule {

    @Binds
    abstract fun provideSharedPreferences(SPManager: SharedPreferencesManagerImpl): SharedPreferencesManager

}