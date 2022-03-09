package com.example.domain_layer.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteDB(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Float,
    val category: String,
    val description: String,
    val imageURL: String,
    val count: Int = 1,
)
