package com.example.domain_layer.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentlyProductDB(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Float,
    val category: String,
    val description: String,
    val imageURL: String,
    val timestamp: Long
)
