package com.example.trempelapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    protected open fun configureAppBar() {
        hideAppBar()
    }

    protected open fun handleArguments() {}

    protected fun changeAppBarTitle(title: String) {
        if (activity is AppBarHandler) {
            (activity as AppBarHandler).changeTitle(title)
        }
    }

    protected fun hideAppBar() {
        if (activity is AppBarHandler) {
            (activity as AppBarHandler).hideAppBar()
        }
    }

    protected fun showAppBar() {
        if (activity is AppBarHandler) {
            (activity as AppBarHandler).showAppBar()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDagger()
        super.onCreate(savedInstanceState)
        handleArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState)
        configureAppBar()
        return rootView
    }

    protected abstract fun injectDagger()
}
