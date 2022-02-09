package com.example.trempelapp.data_layer.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Float,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val imageURL: String,
    @SerializedName("rating")
    val rating: Rating?,
    val isFavourite: Boolean = false,
    val count: Int = 1,
    val timestamp: Long = 0
) : Parcelable {
    var isChecked = false
}

@Parcelize
data class Rating(
    @SerializedName("rate")
    val rate: Float,
    @SerializedName("count")
    val commentsCount: Int,
) : Parcelable
