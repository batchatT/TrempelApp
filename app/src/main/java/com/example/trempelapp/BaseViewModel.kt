package com.example.trempelapp

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    abstract fun injectDagger(application: TrempelApplication)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
