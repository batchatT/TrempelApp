package com.example.trempelapp.presentation_layer.main_screen.main_fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.SearchQueryException
import com.example.domain_layer.coroutine_use_cases.InsertProductToCartDBUseCaseImpl
import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.rxjava_use_cases.FindProductsBySearchUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.GetAllFavouritesUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.execute
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.presentation_layer.main_screen.main_fragments.product.ProductListItem
import com.example.trempelapp.presentation_layer.main_screen.main_fragments.product.ProductRecyclerAdapter
import com.example.trempelapp.utils.EMPTY_STRING
import com.example.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchPageFragmentViewModel @Inject constructor(
    private val fetchProductsByCategory: FindProductsBySearchUseCaseImpl,
    private val fetchAllFavourites: GetAllFavouritesUseCaseImpl,
    private val insertProductToCart: InsertProductToCartDBUseCaseImpl
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

    val errorQueryLiveData = SingleLiveEvent<String>()

    val queryTextLiveData = MutableLiveData<String>()

    val isSearchEmptyLiveData = SingleLiveEvent<Boolean>()

    val isErrorLiveData = MutableLiveData<Boolean>()

    val productListLiveData: SingleLiveEvent<List<ProductListItem>>
        get() = _productListLiveData
    private val _productListLiveData = SingleLiveEvent<List<ProductListItem>>()

    val onProductClickedLiveData: SingleLiveEvent<ProductMainModel>
        get() = _onProductClickedLiveData
    private val _onProductClickedLiveData = SingleLiveEvent<ProductMainModel>()

    val favouritesListLiveData: LiveData<List<ProductMainModel>>
        get() = _favouritesListLiveData
    private val _favouritesListLiveData = fetchAllFavourites.execute()

    val onAddProductToCartLiveData: LiveData<Void>
        get() = _onAddProductToCartLiveData
    private val _onAddProductToCartLiveData = SingleLiveEvent<Void>()

    private val onAddProductToCartClickObserver = Observer<ProductMainModel> {
        onAddToCartClicked(it)
    }

    fun getFilteredListOfProducts(searchText: String) {
        fetchProductsByCategory
            .execute(searchText)
            .map { productList ->
                productList.map { product ->
                    ProductListItem(product)
                }
            }
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
                    .map { ProductListItem(it.product) }
                    .onEach { productListItem ->
                        productListItem.onAddToCartClickLiveData
                            .observeForever(onAddProductToCartClickObserver)
                    }
                if (_productListLiveData.value?.size == 0) {
                    isSearchEmptyLiveData.value = true
                }
            }, { error ->
                if (error is SearchQueryException) {
                    handleSearchQueryError(error)
                    isErrorLiveData.value = true
                } else {
                    handleError(error)
                }
            })
            .run(compositeDisposable::add)
    }

    fun searchProduct(searchText: String) {
        getFilteredListOfProducts(searchText)
        isSearchEmptyLiveData.value = false
        isErrorLiveData.value = false
        _productListLiveData.value = emptyList()
    }

    fun clearQueryError() {
        if (!queryFieldIsEmpty()) {
            errorQueryLiveData.value = EMPTY_STRING
        }
    }

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

    private fun onAddToCartClicked(product: ProductMainModel) {
        viewModelScope.launch {
            insertProductToCart.execute(product)
        }
        _onAddProductToCartLiveData.call()
    }

    private fun queryFieldIsEmpty(): Boolean {
        if (queryTextLiveData.value.isNullOrEmpty()) {
            return true
        }
        return false
    }

    private fun handleSearchQueryError(error: SearchQueryException) {
        error.queryError?.let {
            errorQueryLiveData.value
        }
    }

    override fun onCleared() {
        _productListLiveData.value
            ?.forEach {
                it.onAddToCartClickLiveData.removeObserver(onAddProductToCartClickObserver)
            }
        super.onCleared()
    }
}
