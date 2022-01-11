package com.example.trempelapp.domain_layer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.trempelapp.R

enum class Category(@StringRes val stringId: Int, @DrawableRes val image: Int) {
    ALL(R.string.all_products, R.drawable.all_products),
    ELECTRONICS(R.string.electronics, R.drawable.electronics),
    WOMENS_CLOTHING(R.string.womens_clothing, R.drawable.womens_clothing),
    MENS_CLOTHING(R.string.mens_clothing, R.drawable.mens_clothing),
    JEWELERY(R.string.jewelery, R.drawable.jewelery),
}
