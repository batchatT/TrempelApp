package com.example.trempelapp.presentation_layer.logInScreen

import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.domain_layer.LogInUseCaseImpl
import com.example.trempelapp.domain_layer.WriteTokenUseCaseImpl
import com.example.trempelapp.utils.EMPTY_STRING
import com.example.trempelapp.utils.LoginException
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "TrempelLogInViewModel"

class TrempelLogInViewModel : BaseViewModel() {

    @Inject
    lateinit var logInUseCase: LogInUseCaseImpl

    @Inject
    lateinit var writeTokenUseCase: WriteTokenUseCaseImpl

    val errorLoginEditLiveData = SingleLiveEvent<String>()
    val editLoginTextLiveData = SingleLiveEvent<String>()
    val editPassWordTextLiveData = SingleLiveEvent<String>()
    val errorPasswordEditLiveData = SingleLiveEvent<String>()

    val tokenLiveData: SingleLiveEvent<String>
        get() = _tokenLiveData
    private val _tokenLiveData = SingleLiveEvent<String>()

    override fun injectDagger(application: TrempelApplication) {
        application.trempelApp.inject(this)
    }

    private fun logInUser(login: String, password: String) {
        logInUseCase
            .execute(UserCredentials(login, password))
            .doOnSubscribe {
                isLoadingLiveData.postValue(true)
            }
            .doFinally {
                isLoadingLiveData.postValue(false)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    writeTokenUseCase.execute(it)
                    _tokenLiveData.value = it
                },
                {
                    if (it is LoginException) {
                        errorLoginEditLiveData.value = it.loginErrorText
                        errorPasswordEditLiveData.value = it.passwordErrorText
                    } else {
                        handleError(it)
                    }
                }
            )
            .run(compositeDisposable::add)
    }

    fun onClickLoginButton() {
        val loginText =
            if (editLoginTextLiveData.value.isNullOrEmpty()) ""
            else editLoginTextLiveData.value!!

        val passwordText =
            if (editPassWordTextLiveData.value.isNullOrEmpty()) ""
            else editPassWordTextLiveData.value!!

        logInUser(loginText, passwordText)
    }

    fun clearLoginError() {
        if (!logInFieldIsEmpty()) {
            errorLoginEditLiveData.value = EMPTY_STRING
        }
    }

    fun clearPasswordLiveData() {
        if (!passwordFieldIsEmpty()) {
            errorPasswordEditLiveData.value = EMPTY_STRING
        }
    }

    private fun logInFieldIsEmpty(): Boolean {
        if (editLoginTextLiveData.value.isNullOrEmpty()) {
            return true
        }
        return false
    }

    private fun passwordFieldIsEmpty(): Boolean {
        if (editPassWordTextLiveData.value.isNullOrEmpty()) {
            return true
        }
        return false
    }
}
