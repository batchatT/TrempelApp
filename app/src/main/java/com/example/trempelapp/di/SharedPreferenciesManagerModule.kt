package com.example.trempelapp.di

import com.example.trempelapp.data_layer.inMemory.SharedPreferencesManager
import com.example.trempelapp.data_layer.inMemory.SharedPreferencesManagerImpl
import dagger.Binds
import dagger.Module

@Module
abstract class SharedPreferencesManagerModule {

    @Binds
    abstract fun provideSharedPreferences(SPManager: SharedPreferencesManagerImpl): SharedPreferencesManager

}