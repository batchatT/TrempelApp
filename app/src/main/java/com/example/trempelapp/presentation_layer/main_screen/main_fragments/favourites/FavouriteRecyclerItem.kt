package com.example.trempelapp.presentation_layer.main_screen.main_fragments.favourites

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.example.trempelapp.data_layer.models.Favourite

private const val TAG = "FavouriteRecyclerItem"

class FavouriteRecyclerItem(
    val favouriteDB: Favourite,
    private val changeStatusFavouriteLiveData: MutableLiveData<Favourite>
) : DefaultLifecycleObserver {

    val quantityLiveData = MutableLiveData(favouriteDB.count.toString())
    val isIncreasableLiveData = MutableLiveData(favouriteDB.count <= 10)
    val isDecreasableLiveData = MutableLiveData(favouriteDB.count > 1)

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

    fun onAddToCartClick() {
        favouriteDB.isChecked = !favouriteDB.isChecked
        changeStatusFavouriteLiveData.value = favouriteDB
        Log.d(TAG, "onAddToCartClick: $favouriteDB ${favouriteDB.isChecked}")
    }
}
