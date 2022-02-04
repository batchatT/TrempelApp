package com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteDB(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Float,
    val imageURL: String,
    val count: Int = 1
)
