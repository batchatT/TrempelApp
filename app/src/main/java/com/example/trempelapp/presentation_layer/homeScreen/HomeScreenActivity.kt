package com.example.trempelapp.presentation_layer.homeScreen

import android.os.Bundle
import com.example.trempelapp.BaseActivity
import com.example.trempelapp.R

class HomeScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun injectDagger() {
    }
}
