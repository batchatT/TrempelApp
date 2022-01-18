package com.example.trempelapp.utils

import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.trempelapp.R
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

private const val TAG = "IMAGE"
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide
        .with(view.context)
        .load(url)
        .placeholder(R.drawable.ic_splash_activity_image)
        .into(view)
}

@BindingAdapter("setPrice")
fun setPrice(view: TextView, price: Float?) {
    view.text = view.context.getString(R.string.price, price)
}

@BindingAdapter("setRating")
fun setRating(view: TextView, rating: Float?) {
    view.text = rating.toString()
}

@BindingAdapter("setFirstUpperCase")
fun setFirstUpperCase(view: TextView, title: String) {
    title.replaceFirstChar { it.uppercaseChar() }
}

@BindingAdapter("titleResId")
fun setTitleResId(view: TextView, @StringRes title: Int) {
    view.setText(title)
}

@BindingAdapter("scrollable")
fun setScrollable(view: TextView, scrollable: Boolean) {
    if (scrollable) {
        view.movementMethod = ScrollingMovementMethod()
    }
}
