package com.example.trempelapp.di

import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.presentationLayer.splashScreen.TrempelSplashViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class TrempelSplashViewModelModule {

    @Binds
    abstract fun provideTrempelSplashViewModelFactory(factory: TrempelSplashViewModelFactory): ViewModelProvider.Factory
}
