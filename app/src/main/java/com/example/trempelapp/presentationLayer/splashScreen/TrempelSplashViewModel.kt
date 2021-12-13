package com.example.trempelapp.presentationLayer.splashScreen

import androidx.lifecycle.ViewModel
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import com.example.trempelapp.domainLayer.UseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class TrempelSplashViewModel @Inject constructor(
    private val userInfoUseCase: UseCase<UserCredentials, Single<LoginResponse>>
    ): BaseViewModel(){

    fun fetchData() {
        userInfoUseCase
            .execute(UserCredentials("sdf", "dsf"))
            .map { it }
            .subscribe()
            .run(compositeDisposable::add)
    }

}
