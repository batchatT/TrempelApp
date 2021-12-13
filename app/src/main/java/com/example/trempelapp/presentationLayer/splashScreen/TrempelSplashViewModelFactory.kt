package com.example.trempelapp.presentationLayer.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import com.example.trempelapp.domainLayer.UseCase
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

class TrempelSplashViewModelFactory @Inject constructor(
    private val userInfoUseCase: UseCase<UserCredentials, Single<LoginResponse>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrempelSplashViewModel::class.java)) {
            return TrempelSplashViewModel(userInfoUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
