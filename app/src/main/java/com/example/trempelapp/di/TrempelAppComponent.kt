package com.example.trempelapp.di

import com.example.trempelapp.presentationLayer.splashScreen.TrempelSplashActivity
import dagger.Component

@Component(
    modules = [
        TrempelSplashViewModelModule::class,
        GetUserInfoUseCaseModule::class,
        UserInfoRepositoryModule::class,
        SharedPreferencesManagerModule::class,
        RetrofitModule::class]
)
interface TrempelAppComponent {

    fun inject(trempelSplashActivity: TrempelSplashActivity)

}
