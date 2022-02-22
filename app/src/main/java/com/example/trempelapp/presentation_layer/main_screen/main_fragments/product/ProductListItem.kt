package com.example.trempelapp.presentation_layer.main_screen.main_fragments.product

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.utils.SingleLiveEvent

class ProductListItem(
    val product: Product,
) {

    val onAddToCartClickLiveData = SingleLiveEvent<Product>()

    fun onAddToCartClick() {
        onAddToCartClickLiveData.value = product
    }
}
