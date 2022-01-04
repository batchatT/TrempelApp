package com.example.trempelapp

import androidx.fragment.app.Fragment
import com.example.trempelapp.presentation_layer.ViewModelProviderFactory
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    protected fun handleErrors(it: String) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).handleErrors(it)
        }
    }

    protected abstract fun injectDagger()
}
