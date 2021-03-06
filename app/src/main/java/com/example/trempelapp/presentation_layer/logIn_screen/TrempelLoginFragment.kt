package com.example.trempelapp.presentation_layer.logIn_screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentTrempelLoginBinding
import com.example.trempelapp.presentation_layer.main_screen.HomeScreenActivity

class TrempelLoginFragment : BaseFragment() {

    companion object {
        fun newInstance(): TrempelLoginFragment {

            val args = Bundle()

            val trempelLoginFragment = TrempelLoginFragment()
            trempelLoginFragment.arguments = args
            return trempelLoginFragment
        }
    }

    private val trempelLogInViewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[TrempelLogInViewModel::class.java]
    }

    private val binding by lazy {
        FragmentTrempelLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setUpBinding()
        setUpObserves()
        return binding.root
    }

    private fun setUpBinding() {
        binding.viewModel = trempelLogInViewModel
        binding.lifecycleOwner = this
    }

    private fun setUpObserves() {
        trempelLogInViewModel.errorLiveData.observe(viewLifecycleOwner) {
            handleErrors(it)
        }

        trempelLogInViewModel.tokenLiveData.observe(viewLifecycleOwner) {
            val intent = Intent(context, HomeScreenActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        trempelLogInViewModel.editLoginTextLiveData.observe(viewLifecycleOwner) {
            trempelLogInViewModel.clearLoginError()
        }

        trempelLogInViewModel.editPassWordTextLiveData.observe(viewLifecycleOwner) {
            trempelLogInViewModel.clearPasswordLiveData()
        }
    }

    override fun injectDagger() {
        (requireActivity().application as TrempelApplication).trempelApp.inject(this)
    }
}
