package com.example.trempelapp.presentation_layer.splash_screen

import android.util.Log
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.domain_layer.LoginStatusUseCaseImpl
import com.example.trempelapp.domain_layer.execute
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val SPLASH_SCREEN_TIMER = 2L

class TrempelSplashViewModel : BaseViewModel() {

    @Inject
    lateinit var userLoginStatusUseCase: LoginStatusUseCaseImpl

    val isLoggedIn: SingleLiveEvent<Boolean>
        get() = _isLoggedIn
    private var _isLoggedIn = SingleLiveEvent<Boolean>()

    override fun injectDagger(application: TrempelApplication) {
        application.trempelApp.inject(this)
    }

    fun checkUserStatus() {
        userLoginStatusUseCase
            .execute()
            .delay(SPLASH_SCREEN_TIMER, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _isLoggedIn.value = it
                },
                {
                    Log.e("TAG", "checkUserStatus:", it)
                }
            )
            .run(compositeDisposable::add)
    }
}
