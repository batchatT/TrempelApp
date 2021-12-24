package com.example.trempelapp

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected fun handleErrors(it: String) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).handleErrors(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDagger()
        super.onCreate(savedInstanceState)
    }

    protected abstract fun injectDagger()
}
