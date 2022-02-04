package com.example.trempelapp.data_layer.models

data class Favourite(
    val id: Int,
    val title: String,
    val price: Float,
    val imageURL: String,
    val count: Int = 1
) {
    var isChecked = false
}
