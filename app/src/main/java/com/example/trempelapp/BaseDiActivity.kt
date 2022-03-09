package com.example.trempelapp

import android.os.Bundle

abstract class BaseDiActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDagger()
        super.onCreate(savedInstanceState)
    }

    protected abstract fun injectDagger()
}
