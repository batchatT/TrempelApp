package com.example.trempelapp.presentation_layer.logInScreen

import android.os.Bundle
import com.example.trempelapp.BaseActivity
import com.example.trempelapp.R

class LogInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_log_in)
    }
}
