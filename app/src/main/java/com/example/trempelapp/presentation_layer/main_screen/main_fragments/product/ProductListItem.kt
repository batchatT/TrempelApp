package com.example.trempelapp.presentation_layer.main_screen.main_fragments.product

import com.example.domain_layer.models.ProductMainModel
import com.example.utils.SingleLiveEvent

class ProductListItem(
    val product: ProductMainModel,
) {

    val onAddToCartClickLiveData = SingleLiveEvent<ProductMainModel>()

    fun onAddToCartClick() {
        onAddToCartClickLiveData.value = product
    }
}
