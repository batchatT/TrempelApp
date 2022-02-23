package com.example.trempelapp.presentation_layer.main_screen.main_fragments.cart

import androidx.lifecycle.MutableLiveData
import com.example.domain_layer.models.ProductMainModel

class CartRecyclerItem(
    val cartItem: ProductMainModel,
) {

    val quantityLiveData = MutableLiveData(cartItem.count.toString())
    val isIncreasableLiveData = MutableLiveData(cartItem.count <= 10)
    val isDecreasableLiveData = MutableLiveData(cartItem.count > 1)

    private val count
        get() = quantityLiveData.value?.toInt() ?: 0

    fun increaseQuantity() {
        val newValue = count.plus(1)
        if (newValue == 10) {
            isIncreasableLiveData.value = false
        }
        if (newValue > 1) {
            isDecreasableLiveData.value = true
        }
        quantityLiveData.value = newValue.toString()
    }

    fun decreaseQuantity() {
        val newValue = count.minus(1)
        if (newValue == 1) {
            isDecreasableLiveData.value = false
        }
        if (newValue < 10) {
            isIncreasableLiveData.value = true
        }
        quantityLiveData.value = newValue.toString()
    }

    fun isIncreasable() {
        if (quantityLiveData.value?.toInt() == 10) {
            isIncreasableLiveData.value = false
        }
    }
}
