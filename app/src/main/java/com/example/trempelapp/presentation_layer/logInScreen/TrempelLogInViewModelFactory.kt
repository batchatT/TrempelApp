package com.example.trempelapp.presentation_layer.logInScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class TrempelLogInViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrempelLogInViewModel::class.java)) {
            return TrempelLogInViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
