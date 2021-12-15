package com.example.trempelapp

import androidx.lifecycle.ViewModel
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val errorLiveData: SingleLiveEvent<String> = SingleLiveEvent()

    protected fun handleError(throwable: Throwable) {
        errorLiveData.value = throwable.localizedMessage
    }

    abstract fun injectDagger(application: TrempelApplication)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
