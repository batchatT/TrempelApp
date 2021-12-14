package com.example.trempelapp.presentation_layer.splash_screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseActivity
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.SplashActivityBinding

@SuppressLint("CustomSplashScreen")
class TrempelSplashActivity : BaseActivity() {

    private val trempelSplashViewModel by lazy {
        ViewModelProvider(this, TrempelSplashViewModelFactory())[TrempelSplashViewModel::class.java]
    }

    private val binding by lazy {
        SplashActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TrempelApplication).trempelApp.inject(this)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
    }
}
