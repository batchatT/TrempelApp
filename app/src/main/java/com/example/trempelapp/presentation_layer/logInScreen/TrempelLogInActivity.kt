package com.example.trempelapp.presentation_layer.logInScreen

import android.os.Bundle
import com.example.trempelapp.BaseActivity
import com.example.trempelapp.R
import com.example.trempelapp.databinding.ActivityLogInBinding

class TrempelLogInActivity : BaseActivity() {

    private val binding by lazy {
        ActivityLogInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        replaceActivityWithFragment(TrempelLoginFragment.newInstance(), R.id.login_activity_container)
    }
}
