package com.example.trempelapp.presentation_layer.splash_screen

import com.example.domain_layer.rxjava_use_cases.LoginStatusUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.execute
import com.example.trempelapp.BaseViewModel
import com.example.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val SPLASH_SCREEN_TIMER = 2L

class TrempelSplashViewModel @Inject constructor(
    private val userLoginStatusUseCase: LoginStatusUseCaseImpl,
) : BaseViewModel() {

    val isLoggedInLiveData: SingleLiveEvent<Boolean>
        get() = _isLoggedInLiveData
    private var _isLoggedInLiveData = SingleLiveEvent<Boolean>()

    fun checkUserStatus() {
        userLoginStatusUseCase
            .execute()
            .delay(SPLASH_SCREEN_TIMER, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _isLoggedInLiveData.value = it
                },
                {
                    handleError(it)
                }
            )
            .run(compositeDisposable::add)
    }
}
