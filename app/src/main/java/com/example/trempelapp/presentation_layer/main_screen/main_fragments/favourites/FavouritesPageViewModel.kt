package com.example.trempelapp.presentation_layer.main_screen.main_fragments.favourites

import androidx.lifecycle.MutableLiveData
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.domain_layer.DeleteFavouriteUseCaseImpl
import com.example.trempelapp.domain_layer.FindFavouritesUseCaseImpl
import com.example.trempelapp.domain_layer.InsertFavouriteUseCaseImpl
import com.example.trempelapp.domain_layer.execute
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "FavouritesPageViewModel"

class FavouritesPageViewModel @Inject constructor(
    private val findFavourites: FindFavouritesUseCaseImpl,
    private val deleteFavourite: DeleteFavouriteUseCaseImpl,
    private val insertFavourite: InsertFavouriteUseCaseImpl
) : BaseViewModel() {

    val adapter by lazy {
        FavouritesRecyclerAdapter(changeStatusFavouriteLiveData, favouriteToRemoveLiveData).apply {
            setOnProductListener(object : FavouritesRecyclerAdapter.OnFavouriteListener {
                override fun onFavouriteListener(favourite: Product) {
                    _product = favourite
                    _onProductClickedLiveData.call()
                }
            })
        }
    }

    val product: Product?
        get() = _product

    val changeStatusFavouriteLiveData = MutableLiveData<Product>()
    val favouriteToRemoveLiveData = SingleLiveEvent<Product>()
    val isAddToCartButtonEnabledLiveData: SingleLiveEvent<Boolean>
        get() = _isAddToCartButtonEnabledLiveData
    val favouriteListLiveData: SingleLiveEvent<List<Product>>
        get() = _favouriteListLiveData
    val onProductClickedLiveData: SingleLiveEvent<Void>
        get() = _onProductClickedLiveData
    private val _isAddToCartButtonEnabledLiveData = SingleLiveEvent<Boolean>()
        .apply {
            value = false
        }
    private var _product: Product? = null
    private val _favouriteListLiveData = SingleLiveEvent<List<Product>>()
    private val _onProductClickedLiveData = SingleLiveEvent<Void>()
    private val favouriteList = mutableListOf<Product>()

    fun getAllFavourites() {
        findFavourites
            .execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _favouriteListLiveData.value = it
                favouriteList.clear()
                favouriteList.addAll(it)
                updateCartButton()
            }, {
                handleError(it)
            })
            .run(compositeDisposable::add)
    }

    private fun deleteFavouriteFromDB(favourite: Product) {
        deleteFavourite
            .execute(favourite)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                favouriteList.remove(favourite)
                updateCartButton()
            }, {
                handleError(it)
            })
            .run(compositeDisposable::add)
    }

    fun updateCartButton() {
        _isAddToCartButtonEnabledLiveData.value = favouriteList.any { it.isChecked }
    }

    fun insertFavourite(position: Int) {
        favouriteToRemoveLiveData.value?.let {
            insertFavourite
                .execute(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .run(compositeDisposable::add)
            favouriteList.add(it)
        }
        adapter.insertItem(position)
        updateCartButton()
    }

    fun removeFavouriteItem(favourite: Product) {
        deleteFavouriteFromDB(favourite)
    }
}
