package com.example.trempelapp.presentation_layer.logInScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentTrempelLoginBinding
import com.example.trempelapp.presentation_layer.mainScreen.HomeScreenActivity

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
        ViewModelProvider(this, TrempelLogInViewModelFactory())[TrempelLogInViewModel::class.java]
    }

    private val binding by lazy {
        FragmentTrempelLoginBinding.inflate(layoutInflater)
    }

    override fun injectDagger() {
        trempelLogInViewModel.injectDagger(requireActivity().application as TrempelApplication)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpBinding()
        setUpObserves()
        return binding.root
    }

    private fun setUpBinding() {
        binding.viewModel = trempelLogInViewModel
        binding.lifecycleOwner = this
    }

    private fun setUpObserves() {
        trempelLogInViewModel.errorLiveData.observe(this, {
            handleErrors(it)
        })

        trempelLogInViewModel.tokenLiveData.observe(this, {
            val intent = Intent(context, HomeScreenActivity::class.java)
            startActivity(intent)
            activity?.finish()
        })

        trempelLogInViewModel.editLoginTextLiveData.observe(viewLifecycleOwner, {
            trempelLogInViewModel.clearLoginError()
        })

        trempelLogInViewModel.editPassWordTextLiveData.observe(viewLifecycleOwner, {
            trempelLogInViewModel.clearPasswordLiveData()
        })
    }
}
