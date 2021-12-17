package com.example.trempelapp.presentation_layer.logInScreen

import android.graphics.Color
import android.telephony.mbms.StreamingServiceInfo
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.databinding.Bindable
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.data_layer.models.auth.UserCredentials
import com.example.trempelapp.domain_layer.LogInUseCaseImpl
import com.example.trempelapp.domain_layer.WriteTokenUseCaseImpl
import com.example.trempelapp.utils.EMPTY_STRING
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.math.log

class TrempelLogInViewModel : BaseViewModel() {

    @Inject
    lateinit var logInUseCase: LogInUseCaseImpl

    @Inject
    lateinit var writeTokenUseCase: WriteTokenUseCaseImpl

    val editLoginText = SingleLiveEvent<String>()
    val editPassWordText = SingleLiveEvent<String>()

    fun onClickLoginButton() {
            logInUser(editLoginText.value!!, editPassWordText.value!!)
    }

    override fun injectDagger(application: TrempelApplication) {
        application.trempelApp.inject(this)
    }

    val tokenLiveData: SingleLiveEvent<String>
        get() = _tokenLiveData
    private val _tokenLiveData = SingleLiveEvent<String>()

    private fun logInUser(login: String, password: String) {
        logInUseCase
            .execute(UserCredentials(login, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    writeTokenUseCase.execute(it)
                    _tokenLiveData.value = it
                    Log.d("success", it)
                },
                {
                    handleError(it)
                })
            .run(compositeDisposable::add)
    }
}
