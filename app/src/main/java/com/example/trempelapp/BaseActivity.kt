package com.example.trempelapp

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.example.trempelapp.utils.ERROR_DIALOG_KEY

abstract class BaseActivity : AppCompatActivity() {

    internal fun handleErrors(it: String) {
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

    fun replaceActivityWithFragment(
        fragment: BaseFragment,
        @IdRes containerViewId: Int,
        tag: String? = null
    ) {
        val fragmentTag = tag ?: BaseFragment::javaClass.name

        if (supportFragmentManager.findFragmentByTag(fragmentTag) != null) {
            return
        }
        supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    protected open fun injectDagger() {
    }
}
