package com.example.domain_layer.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Float,
    @SerializedName("description")
    val description: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("image")
    val imageURL: String,
    @SerializedName("rating")
    val rating: Rating?,
    val isFavourite: Boolean = false,
    var count: Int = 1,
    val timestamp: Long = 0
)

data class Rating(
    @SerializedName("rate")
    val rate: Float,
    @SerializedName("count")
    val commentsCount: Int,
)
