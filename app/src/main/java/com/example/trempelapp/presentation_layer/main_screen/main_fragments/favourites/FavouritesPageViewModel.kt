package com.example.trempelapp.presentation_layer.main_screen.main_fragments.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.domain_layer.coroutine.DeleteFavouritesListFromFavouriteDBUseCaseImpl
import com.example.trempelapp.domain_layer.coroutine.InsertListOfProductsToCartUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.DeleteFavouriteUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.FindFavouritesUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.InsertFavouriteUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.execute
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FavouritesPageViewModel"

class FavouritesPageViewModel @Inject constructor(
    private val findFavourites: FindFavouritesUseCaseImpl,
    private val deleteFavourite: DeleteFavouriteUseCaseImpl,
    private val deleteListOfFavourites: DeleteFavouritesListFromFavouriteDBUseCaseImpl,
    private val insertFavourite: InsertFavouriteUseCaseImpl,
    private val insertToCart: InsertListOfProductsToCartUseCaseImpl,
) : BaseViewModel() {

    val adapter by lazy {
        FavouritesRecyclerAdapter(
            favouriteToRemoveLiveData,
        ).apply {
            setOnProductListener(object : FavouritesRecyclerAdapter.OnFavouriteListener {
                override fun onFavouriteListener(favourite: FavouriteRecyclerItem) {
                    _onProductClickedLiveData.value = favourite.favourite
                }
            })
        }
    }

    val onAddToCartClicked = SingleLiveEvent<Void>()

    val favouriteToRemoveLiveData = SingleLiveEvent<FavouriteRecyclerItem>()
    val isAddToCartButtonEnabledLiveData = MediatorLiveData<Boolean>()

    val favouriteListLiveData: SingleLiveEvent<List<FavouriteRecyclerItem>>
        get() = _favouriteListLiveData
    val onProductClickedLiveData: SingleLiveEvent<Product>
        get() = _onProductClickedLiveData

    private val _favouriteListLiveData = SingleLiveEvent<List<FavouriteRecyclerItem>>()
    private val _onProductClickedLiveData = SingleLiveEvent<Product>()

    val listSizeLiveData: LiveData<Int> = Transformations.map(_favouriteListLiveData) { it.size }

    init {
        isAddToCartButtonEnabledLiveData.addSource(_favouriteListLiveData) {
            updateCartButton()
        }
    }

    fun onAddToCartButtonClick() {
        val productsToCart = _favouriteListLiveData.value
            ?.filter {
                it.isCheckedLiveData.value == true
            }?.map { it.favourite } ?: return

        viewModelScope.launch {
            insertToCart.execute(productsToCart)
            deleteListOfFavourites.execute(productsToCart)
            getAllFavourites()
        }
        updateCartButton()
        onAddToCartClicked.call()
    }

    fun getAllFavourites() {
        findFavourites
            .execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ products ->
                _favouriteListLiveData.value = products
                    .map { FavouriteRecyclerItem(it) }
                    .onEach {
                        isAddToCartButtonEnabledLiveData.addSource(it.isCheckedLiveData) {
                            updateCartButton()
                        }
                    }
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
                _favouriteListLiveData.value = _favouriteListLiveData.value
                    ?.filter { it.favourite != favourite }
            }, {
                handleError(it)
            })
            .run(compositeDisposable::add)
    }

    private fun updateCartButton() {
        isAddToCartButtonEnabledLiveData.value = _favouriteListLiveData.value
            ?.any { it.isCheckedLiveData.value == true }
    }

    fun insertFavourite(position: Int) {
        favouriteToRemoveLiveData.value?.let { item ->
            insertFavourite
                .execute(item.favourite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val products = _favouriteListLiveData.value?.toMutableList()
                    products?.add(position, item)
                    products?.let { _favouriteListLiveData.postValue(it) }
                }, {
                    handleError(it)
                })
                .run(compositeDisposable::add)
        }
        updateCartButton()
    }

    fun removeFavouriteItem(favourite: Product) {
        deleteFavouriteFromDB(favourite)
    }
}
