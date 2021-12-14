package com.example.trempelapp.presentation_layer.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import com.example.trempelapp.domain_layer.UseCase
import dagger.Provides
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class TrempelSplashViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrempelSplashViewModel::class.java)) {
            return TrempelSplashViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
