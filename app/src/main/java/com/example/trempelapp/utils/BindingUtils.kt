package com.example.trempelapp.utils

import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun setError(textInputLayout: TextInputLayout, errorText: String?) {
    textInputLayout.error = errorText
}

@BindingAdapter("setVisibility")
fun setVisibility(progressBar: ProgressBar, isVisible: Boolean) {
    if (isVisible) {
        progressBar.visibility = ProgressBar.VISIBLE
    } else {
        progressBar.visibility = ProgressBar.GONE
    }
}
