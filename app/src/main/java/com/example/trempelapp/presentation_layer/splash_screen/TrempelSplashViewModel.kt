package com.example.trempelapp.presentation_layer.splash_screen

import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.data_layer.network.LoginResponse
import com.example.trempelapp.domain_layer.UseCase
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class TrempelSplashViewModel @Inject constructor(): BaseViewModel(){

    @Named("authUseCaseImpl")
    @Inject
    lateinit var userInfoUseCase: UseCase<UserCredentials, Single<LoginResponse>>

    fun fetchData() {
        userInfoUseCase
            .execute(UserCredentials("sdf", "dsf"))
            .map { it }
            .subscribe()
            .run(compositeDisposable::add)
    }

}
