package com.example.trempelapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.trempelapp.utils.ERROR_DIALOG_KEY

class ErrorDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val args = arguments?.getString(ERROR_DIALOG_KEY)
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage(args)
                .setPositiveButton(getString(R.string.ok)) { _, _ -> }
                .create()
        }
    }

    companion object {
        const val TAG = "ErrorDialog"
    }
}
