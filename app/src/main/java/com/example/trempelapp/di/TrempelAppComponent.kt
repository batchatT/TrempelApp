package com.example.trempelapp.di

import android.content.Context
import com.example.trempelapp.presentation_layer.logInScreen.TrempelLogInViewModel
import com.example.trempelapp.presentation_layer.logInScreen.TrempelLoginFragment
import com.example.trempelapp.presentation_layer.splash_screen.TrempelSplashActivity
import com.example.trempelapp.presentation_layer.splash_screen.TrempelSplashViewModel
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        RepositoryModule::class,
        RetrofitModule::class
    ]
)
interface TrempelAppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TrempelAppComponent
    }

    fun inject(viewModel: TrempelSplashViewModel)
    fun inject(trempelSplashActivity: TrempelSplashActivity)

    fun inject(viewModel: TrempelLogInViewModel)

    fun inject(trempelLogInFragment: TrempelLoginFragment)
}
