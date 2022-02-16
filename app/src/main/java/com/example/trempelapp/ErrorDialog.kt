package com.example.trempelapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.trempelapp.databinding.ErrorDialogLayoutBinding
import com.example.trempelapp.utils.ERROR_DIALOG_KEY

class ErrorDialog : DialogFragment() {

    companion object {
        const val TAG = "ErrorDialog"
    }

    private val binding by lazy {
        ErrorDialogLayoutBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val args = arguments?.getString(ERROR_DIALOG_KEY)
        binding.errorTitle.text = args
        binding.closeButton.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}
