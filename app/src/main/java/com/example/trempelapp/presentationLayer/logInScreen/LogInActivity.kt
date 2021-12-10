package com.example.trempelapp.presentationLayer.logInScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trempelapp.R

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_log_in)
    }
}