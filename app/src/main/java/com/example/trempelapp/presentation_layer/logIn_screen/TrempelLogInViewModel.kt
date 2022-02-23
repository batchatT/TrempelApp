package com.example.trempelapp.presentation_layer.logIn_screen

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.domain_layer.LoginException
import com.example.domain_layer.models.UserCredentials
import com.example.domain_layer.rxjava_use_cases.LogInUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.WriteTokenUseCaseImpl
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.BuildConfig
import com.example.trempelapp.utils.EMPTY_STRING
import com.example.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "TrempelLogInViewModel"

class TrempelLogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCaseImpl,
    private val writeTokenUseCase: WriteTokenUseCaseImpl,
) : BaseViewModel() {

    val errorLoginEditLiveData = SingleLiveEvent<String>()
    val errorPasswordEditLiveData = SingleLiveEvent<String>()
    val editLoginTextLiveData = MutableLiveData<String>()
    val editPassWordTextLiveData = MutableLiveData<String>()

    val tokenLiveData: SingleLiveEvent<String>
        get() = _tokenLiveData
    private val _tokenLiveData = SingleLiveEvent<String>()

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
                        handleLoginError(it)
                    } else {
                        handleError(it)
                    }
                }
            )
            .run(compositeDisposable::add)
    }

    private fun handleLoginError(error: LoginException) {
        errorLoginEditLiveData.value = error.loginErrorText
        errorPasswordEditLiveData.value = error.passwordErrorText
    }

    fun setLoginDataForDebug(view: View) {
        if (BuildConfig.DEBUG) {
            editLoginTextLiveData.value = "mor_2314"
            editPassWordTextLiveData.value = "83r5^_"
        }
    }

    fun onClickLoginButton() {
        normalizeLoginData()

        val loginText = editLoginTextLiveData.value!!

        val passwordText = editPassWordTextLiveData.value!!

        logInUser(loginText, passwordText)
    }

    private fun normalizeLoginData() {
        val loginText = editLoginTextLiveData.value?.trim() ?: ""
        val passwordText = editPassWordTextLiveData.value?.trim() ?: ""

        editLoginTextLiveData.value = loginText
        editPassWordTextLiveData.value = passwordText
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
