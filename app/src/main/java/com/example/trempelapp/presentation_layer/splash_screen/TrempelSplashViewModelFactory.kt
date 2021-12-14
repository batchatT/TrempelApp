package com.example.trempelapp.presentation_layer.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class TrempelSplashViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrempelSplashViewModel::class.java)) {
            return TrempelSplashViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
