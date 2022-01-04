package com.example.trempelapp.presentation_layer.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseActivity
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.SplashActivityBinding
import com.example.trempelapp.presentation_layer.logInScreen.TrempelLogInActivity
import com.example.trempelapp.presentation_layer.mainScreen.HomeScreenActivity

@SuppressLint("CustomSplashScreen")
class TrempelSplashActivity : BaseActivity() {

    override fun injectDagger() {
        (application as TrempelApplication).trempelApp.inject(this)
    }

    private val trempelSplashViewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[TrempelSplashViewModel::class.java]
    }

    private val binding by lazy {
        SplashActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        trempelSplashViewModel.checkUserStatus()
        trempelSplashViewModel.errorLiveData.observe(this, {
            handleErrors(it)
        })

        trempelSplashViewModel.isLoggedInLiveData.observe(this, {
            startActivityByStatus(it)
        })
    }

    private fun startActivityByStatus(isLoggedIn: Boolean) {
        val intent: Intent = when (isLoggedIn) {
            true -> {
                Intent(this, HomeScreenActivity::class.java)
            }
            else -> {
                Intent(this, TrempelLogInActivity::class.java)
            }
        }
        startActivity(intent)
        finish()
    }
}
