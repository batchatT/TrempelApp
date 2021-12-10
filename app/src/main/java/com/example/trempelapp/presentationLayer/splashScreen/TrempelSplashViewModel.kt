package com.example.trempelapp.presentationLayer.splashScreen

import androidx.lifecycle.ViewModel
import com.example.trempelapp.domainLayer.GetUserInfoUseCase
import javax.inject.Inject

class TrempelSplashViewModel @Inject constructor(
    private val userInfoUseCase: GetUserInfoUseCase
    ): ViewModel(){



}
