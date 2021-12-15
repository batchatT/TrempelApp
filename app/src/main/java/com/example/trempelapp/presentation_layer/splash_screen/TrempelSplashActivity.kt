package com.example.trempelapp.presentation_layer.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseActivity
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.SplashActivityBinding
import com.example.trempelapp.presentation_layer.homeScreen.MainActivity
import com.example.trempelapp.presentation_layer.logInScreen.LogInActivity

@SuppressLint("CustomSplashScreen")
class TrempelSplashActivity : BaseActivity() {

    private val trempelSplashViewModel by lazy {
        ViewModelProvider(this, TrempelSplashViewModelFactory())[TrempelSplashViewModel::class.java]
    }

    private val binding by lazy {
        SplashActivityBinding.inflate(layoutInflater)
    }

    override fun injectDagger() {
        trempelSplashViewModel.injectDagger(application as TrempelApplication)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
injectDagger()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        trempelSplashViewModel.checkUserStatus()
        trempelSplashViewModel.errorLiveData.observe(this, {
            handleErrors(it)
        })

        trempelSplashViewModel.isLoggedInLiveData.observe(this, {

            if (trempelSplashViewModel.isLoggedInLiveData.value == false) {
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}
