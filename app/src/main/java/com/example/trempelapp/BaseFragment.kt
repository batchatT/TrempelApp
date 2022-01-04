package com.example.trempelapp

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDagger()
        super.onCreate(savedInstanceState)
    }

    protected abstract fun injectDagger()
}
