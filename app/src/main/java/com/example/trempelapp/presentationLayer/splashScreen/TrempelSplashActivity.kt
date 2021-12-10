package com.example.trempelapp.presentationLayer.splashScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.SplashActivityBinding
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class TrempelSplashActivity : AppCompatActivity() {

    @Inject
    lateinit var trempelSplashViewModelFactory: TrempelSplashViewModelFactory

    private val trempelSplashViewModel by lazy {
        ViewModelProvider(this, trempelSplashViewModelFactory)[TrempelSplashViewModel::class.java]
    }

    private val binding by lazy{
        SplashActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TrempelApplication).trempelApp.inject(this)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(binding.root)
    }

}
