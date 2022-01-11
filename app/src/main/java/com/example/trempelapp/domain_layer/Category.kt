package com.example.trempelapp.domain_layer

import androidx.annotation.DrawableRes
import com.example.trempelapp.R

enum class Category(val title: String, @DrawableRes val image: Int) {
    ALL("all products", R.drawable.all_products),
    ELECTRONICS("electronics", R.drawable.electronics),
    WOMENS_CLOTHING("women's clothing", R.drawable.womens_clothing),
    MENS_CLOTHING("men's clothing", R.drawable.mens_clothing),
    JEWELERY("jewelery", R.drawable.jewelery),
}
