package com.example.trempelapp.presentation_layer.main_screen.main_fragments.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.coroutine_use_cases.InsertProductToCartDBUseCaseImpl
import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.rxjava_use_cases.FindProductsByCategoryUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.GetAllFavouritesUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.execute
import com.example.trempelapp.BaseViewModel
import com.example.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val fetchProductsByCategory: FindProductsByCategoryUseCaseImpl,
    private val insertProductToCart: InsertProductToCartDBUseCaseImpl,
    private val fetchAllFavourites: GetAllFavouritesUseCaseImpl
) : BaseViewModel() {

    val adapter by lazy {
        ProductRecyclerAdapter().apply {
            setOnProductListener(object : ProductRecyclerAdapter.OnProductListener {
                override fun onProductListener(product: ProductListItem) {
                    _onProductClickedLiveData.value = product.product
                }
            })
        }
    }

    lateinit var title: String

    private val onAddProductToCartClickObserver = Observer<ProductMainModel> {
        onAddToCartClicked(it)
    }

    val productListLiveData: SingleLiveEvent<List<ProductListItem>>
        get() = _productListLiveData
    private val _productListLiveData = SingleLiveEvent<List<ProductListItem>>(emptyList())

    val onAddProductToCartLiveData: LiveData<Void>
        get() = _onAddProductToCartLiveData
    private val _onAddProductToCartLiveData = SingleLiveEvent<Void>()

    val onProductClickedLiveData: SingleLiveEvent<ProductMainModel>
        get() = _onProductClickedLiveData
    private val _onProductClickedLiveData = SingleLiveEvent<ProductMainModel>()

    val favouritesListLiveData: LiveData<List<ProductMainModel>>
        get() = _favouritesListLiveData
    private val _favouritesListLiveData = fetchAllFavourites.execute()

    fun checkFavourites() {
        val productIds = _productListLiveData.value?.map {
            it.product.id
        }
        val favouriteIds = _favouritesListLiveData.value?.map {
            it.id
        }
        favouriteIds?.let {
            val common = productIds?.filter { favouriteIds.contains(it) }
            _productListLiveData.value?.forEach { productList ->
                common?.let {
                    productList.product.isFavourite = productList.product.id in it
                }
            }
        }
    }

    fun fetchProductsByCategory() {
        fetchProductsByCategory
            .execute(title)
            .doOnSubscribe {
                isLoadingLiveData.postValue(true)
            }
            .doFinally {
                isLoadingLiveData.postValue(false)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ productList ->
                _productListLiveData.value = productList
                    .map { ProductListItem(it) }
                    .onEach { productListItem ->
                        productListItem.onAddToCartClickLiveData
                            .observeForever(onAddProductToCartClickObserver)
                    }
            }, { error ->
                handleError(error)
            })
            .run(compositeDisposable::add)
    }

    private fun onAddToCartClicked(product: ProductMainModel) {
        viewModelScope.launch {
            insertProductToCart.execute(product)
        }
        _onAddProductToCartLiveData.call()
    }

    override fun onCleared() {
        _productListLiveData.value
            ?.forEach {
                it.onAddToCartClickLiveData.removeObserver(onAddProductToCartClickObserver)
            }
        super.onCleared()
    }
}
