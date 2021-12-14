package com.example.trempelapp.di

import com.example.trempelapp.presentation_layer.splash_screen.TrempelSplashActivity
import dagger.Component

@Component(
    modules = [
        UseCaseModule::class,
        RepositoryModule::class,
        RetrofitModule::class
    ]
)
interface TrempelAppComponent {

    fun inject(trempelSplashActivity: TrempelSplashActivity)
}
