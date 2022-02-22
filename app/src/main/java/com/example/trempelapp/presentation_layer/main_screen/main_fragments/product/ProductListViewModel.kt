package com.example.trempelapp.presentation_layer.main_screen.main_fragments.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.domain_layer.coroutine.InsertProductToCartDBUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.FindProductsByCategoryUseCaseImpl
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val fetchProductsByCategory: FindProductsByCategoryUseCaseImpl,
    private val insertProductToCart: InsertProductToCartDBUseCaseImpl,
) : BaseViewModel() {

    lateinit var productItem: ProductListItem

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

    private val onAddProductToCartClickObserver = Observer<Product> {
        onAddToCartClicked(it)
    }

    val productListLiveData: SingleLiveEvent<List<ProductListItem>>
        get() = _productListLiveData
    private val _productListLiveData = SingleLiveEvent<List<ProductListItem>>()

    val onAddProductToCartLiveData: LiveData<Void>
        get() = _onAddProductToCartLiveData
    private val _onAddProductToCartLiveData = SingleLiveEvent<Void>()

    val onProductClickedLiveData: SingleLiveEvent<Product>
        get() = _onProductClickedLiveData
    private val _onProductClickedLiveData = SingleLiveEvent<Product>()

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

    private fun onAddToCartClicked(product: Product) {
        viewModelScope.launch {
            insertProductToCart.execute(product)
        }
        _onAddProductToCartLiveData.call()
    }

    override fun onCleared() {
        _productListLiveData.value?.map { it }
            ?.forEach {
                it.onAddToCartClickLiveData.removeObserver(onAddProductToCartClickObserver)
            }
        super.onCleared()
    }
}
