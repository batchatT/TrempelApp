package com.example.trempelapp.presentation_layer.main_screen.main_fragments.product

import android.util.Log
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.domain_layer.FindProductsByCategoryUseCaseImpl
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val fetchProductsByCategory: FindProductsByCategoryUseCaseImpl,
) : BaseViewModel() {

    lateinit var productItem: Product

    val adapter by lazy {
        ProductRecyclerAdapter().apply {
            setOnProductListener(object : ProductRecyclerAdapter.OnProductListener {
                override fun onCategoryListener(product: Product) {
                    productItem = product
                    _onProductClickedLiveData.call()
                }
            })
        }
    }

    val productListLiveData: SingleLiveEvent<List<Product>>
        get() = _productListLiveData
    private val _productListLiveData = SingleLiveEvent<List<Product>>()

    val onProductClickedLiveData: SingleLiveEvent<Void>
        get() = _onProductClickedLiveData
    private val _onProductClickedLiveData = SingleLiveEvent<Void>()

    fun fetchProductsByCategory(categoryTitle: String) {
        fetchProductsByCategory
            .execute(categoryTitle)
            .doOnSubscribe {
                isLoadingLiveData.postValue(true)
            }
            .doFinally {
                isLoadingLiveData.postValue(false)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _productListLiveData.value = it
                Log.d("ProductListFragment", "execute: $it")
            }, {
                handleError(it)
            })
            .run(compositeDisposable::add)
    }
}