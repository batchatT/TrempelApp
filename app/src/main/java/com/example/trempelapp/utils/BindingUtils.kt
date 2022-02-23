package com.example.trempelapp.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.trempelapp.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun setError(textInputLayout: TextInputLayout, errorText: String?) {
    textInputLayout.error = errorText
}

@BindingAdapter("setVisibility")
fun setVisibility(progressBar: ProgressBar, isVisible: Boolean) {
    progressBar.scaleX = if (isVisible) 1f else 0f
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

@BindingAdapter("titleResId")
fun setTitleResId(view: TextView, @StringRes title: Int) {
    view.setText(title)
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("setPdpFavouriteImage")
fun setPdpImage(imageView: ImageView, isFavourite: Boolean) {
    if (isFavourite) {
        imageView.background = imageView.context.getDrawable(R.drawable.ic_favorite_pdp_active)
    } else {
        imageView.background = imageView.context.getDrawable(R.drawable.ic_favorite_button)
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("setProductListFavouriteImage")
fun setProductListImage(imageView: ImageView, isFavourite: Boolean) {
    if (isFavourite) {
        imageView.visibility = ImageView.VISIBLE
    } else {
        imageView.visibility = ImageView.INVISIBLE
    }
}

@BindingAdapter("setEnabled")
fun setEnabled(view: View, isEnabled: Boolean) {
    view.isEnabled = isEnabled
}

@BindingAdapter("itemsCount")
fun setItemsCount(textView: TextView, count: Int) {
    textView.text = textView.context.getString(R.string.items_count, count)
}

@BindingAdapter("visibile")
fun setVisibility(textView: TextView, count: Int) {
    if (count == 0) {
        textView.visibility = TextView.VISIBLE
    } else {
        textView.visibility = TextView.INVISIBLE
    }
}

@BindingAdapter("searchResultVisibility")
fun setVisibility(textView: TextView, isEmpty: Boolean) {
    if (isEmpty) {
        textView.visibility = TextView.VISIBLE
    } else {
        textView.visibility = TextView.INVISIBLE
    }
}

@BindingAdapter("isEnabled")
fun setIsEnabled(button: Button, count: Int) {
    button.isEnabled = count != 0
}

@BindingAdapter("onRefresh")
fun setRefreshListener(
    swipeRefreshLayout: SwipeRefreshLayout,
    onRefresh: () -> Unit,
) {
    swipeRefreshLayout.setOnRefreshListener {
        onRefresh()
    }
}

@BindingAdapter("isRefreshing")
fun isRefreshing(refreshLayout: SwipeRefreshLayout, refreshing: Boolean) {
    refreshLayout.isRefreshing = refreshing
}

@BindingAdapter("isErrorVisible")
fun isErrorVisible(textView: TextView, isError: Boolean) {
    if (isError) {
        textView.visibility = TextView.VISIBLE
    } else {
        textView.visibility = TextView.INVISIBLE
    }
}
