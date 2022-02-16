package com.example.trempelapp.presentation_layer.main_screen.main_fragments.favourites

import androidx.lifecycle.MutableLiveData
import com.example.trempelapp.data_layer.models.Product

private const val TAG = "FavouriteRecyclerItem"

class FavouriteRecyclerItem(
    val favourite: Product,
) {

    val isCheckedLiveData = MutableLiveData(false)
    val quantityLiveData = MutableLiveData(favourite.count.toString())
    val isIncreasableLiveData = MutableLiveData(favourite.count <= 10)
    val isDecreasableLiveData = MutableLiveData(favourite.count > 1)

    private val count
        get() = quantityLiveData.value?.toInt() ?: 0

    fun increaseQuantity() {
        val newValue = count.plus(1)
        favourite.count = newValue
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
}
