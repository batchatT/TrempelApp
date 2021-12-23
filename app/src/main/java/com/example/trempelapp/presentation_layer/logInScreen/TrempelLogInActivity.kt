package com.example.trempelapp.presentation_layer.logInScreen

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseActivity
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.ActivityLogInBinding
import com.example.trempelapp.presentation_layer.homeScreen.HomeScreenActivity

class TrempelLogInActivity : BaseActivity() {

    private val trempelLogInViewModel by lazy {
        ViewModelProvider(this, TrempelLogInViewModelFactory())[TrempelLogInViewModel::class.java]
    }

    private val binding by lazy {
        ActivityLogInBinding.inflate(layoutInflater)
    }

    override fun injectDagger() {
        trempelLogInViewModel.injectDagger(application as TrempelApplication)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
        setUpBinding()
        setUpObserves()
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
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        })

        trempelLogInViewModel.editLoginTextLiveData.observe(this, {
            trempelLogInViewModel.clearLoginError()
        })

        trempelLogInViewModel.editPassWordTextLiveData.observe(this, {
            trempelLogInViewModel.clearPasswordLiveData()
        })
    }
}
