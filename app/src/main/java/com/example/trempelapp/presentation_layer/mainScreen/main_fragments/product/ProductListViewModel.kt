package com.example.trempelapp.presentation_layer.mainScreen.main_fragments.product

import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.domain_layer.FindProductsByCategoryUseCaseImpl
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val fetchProductsByCategory: FindProductsByCategoryUseCaseImpl
) : BaseViewModel() {

    val productListLiveData: SingleLiveEvent<List<Product>>
        get() = _productListLiveData
    private val _productListLiveData = SingleLiveEvent<List<Product>>()

    val onProductClickedLiveData: SingleLiveEvent<Boolean>
        get() = _onProductClickedLiveData
    private val _onProductClickedLiveData = SingleLiveEvent<Boolean>()

    fun fetchProductsByCategory(categoryTitle: String) {
        if (!_productListLiveData.value.isNullOrEmpty()) {
            return
        }

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
            }, {
                handleError(it)
            })
            .run(compositeDisposable::add)
    }

    lateinit var productItem: Product

    val adapter by lazy {
        ProductRecyclerAdapter().apply {
            setOnProductListener(object : ProductRecyclerAdapter.OnProductListener {
                override fun onCategoryListener(product: Product) {
                    productItem = product
                    _onProductClickedLiveData.value = true
                }
            })
        }
    }
}
