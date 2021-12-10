package com.example.trempelapp.presentationLayer.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.domainLayer.GetUserInfoUseCase
import javax.inject.Inject

class TrempelSplashViewModelFactory @Inject constructor(
    private val userInfoUseCase: GetUserInfoUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrempelSplashViewModel::class.java)) {
            return TrempelSplashViewModel(userInfoUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
