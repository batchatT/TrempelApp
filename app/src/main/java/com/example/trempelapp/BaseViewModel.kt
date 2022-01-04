package com.example.trempelapp

import androidx.lifecycle.ViewModel
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val errorLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    val isLoadingLiveData = SingleLiveEvent<Boolean>()

    protected fun handleError(throwable: Throwable) {
        errorLiveData.value = "Something went wrong"
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
