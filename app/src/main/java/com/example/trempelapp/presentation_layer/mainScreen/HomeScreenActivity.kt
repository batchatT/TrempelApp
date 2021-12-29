package com.example.trempelapp.presentation_layer.mainScreen

import android.os.Bundle
import com.example.trempelapp.BaseActivity
import com.example.trempelapp.databinding.ActivityHomeScreenBinding

class HomeScreenActivity : BaseActivity() {

    private val binding by lazy {
        ActivityHomeScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}
