package com.example.trempelapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trempelapp.utils.ERROR_DIALOG_KEY

abstract class BaseActivity : AppCompatActivity() {

    protected fun handleErrors(it: String) {
        val dialog = ErrorDialog()
        val args = Bundle()
        args.putString(ERROR_DIALOG_KEY, it)
        dialog.arguments = args
        dialog.show(supportFragmentManager, ErrorDialog.TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDagger()
        super.onCreate(savedInstanceState)
    }

    protected abstract fun injectDagger()
}
