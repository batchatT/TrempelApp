package com.example.domain_layer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductMainModel(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val imageURL: String,
    val category: String,
    val rating: RatingMainModel?,
    var isFavourite: Boolean = false,
    var count: Int = 1,
    val timestamp: Long = 0
) : Parcelable

@Parcelize
data class RatingMainModel(
    val rate: Float,
    val commentsCount: Int,
) : Parcelable
