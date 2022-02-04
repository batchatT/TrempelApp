package com.example.trempelapp.presentation_layer.main_screen.main_fragments.pdp

import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.RecentlyProduct
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.domain_layer.FindProductByIdUseCaseImpl
import com.example.trempelapp.domain_layer.GetAllRecentlyProductsUseCaseImpl
import com.example.trempelapp.domain_layer.InsertFavouriteUseCaseImpl
import com.example.trempelapp.domain_layer.InsertRecentlyProductUseCaseImpl
import com.example.trempelapp.domain_layer.DeleteFavouriteFromPDPUseCaseImpl
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductDetailsPageViewModel @Inject constructor(
    private val getAllRecentlyProducts: GetAllRecentlyProductsUseCaseImpl,
    private val insertRecentlyProduct: InsertRecentlyProductUseCaseImpl,
    private val getProductById: FindProductByIdUseCaseImpl,
    private val insertFavourite: InsertFavouriteUseCaseImpl,
    private val deleteFavouriteFromPDP: DeleteFavouriteFromPDPUseCaseImpl
) : BaseViewModel() {

    val onProductClickedLiveData: SingleLiveEvent<Void>
        get() = _onProductClickedLiveData
    private val _onProductClickedLiveData = SingleLiveEvent<Void>()

    val recentlyProductListLiveData: SingleLiveEvent<List<RecentlyProduct>>
        get() = _recentlyProductListLiveData
    private val _recentlyProductListLiveData = SingleLiveEvent<List<RecentlyProduct>>()

    val productLiveData: SingleLiveEvent<Product>
        get() = _productLiveData
    private val _productLiveData = SingleLiveEvent<Product>()

    var recentlyProduct: RecentlyProduct? = null

    private fun loadRecentlyProducts(id: Int) {
        getAllRecentlyProducts
            .execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _recentlyProductListLiveData.value = it
            }, {
                handleError(it)
            }).run(compositeDisposable::add)
    }

    fun findProductById(id: Int) {
        getProductById
            .execute(id)
            .doOnSubscribe {
                isLoadingLiveData.postValue(true)
            }
            .doFinally {
                isLoadingLiveData.postValue(false)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _productLiveData.value = it
                insertRecently()
                loadRecentlyProducts(it.id)
            }, {
                handleError(it)
            }).run(compositeDisposable::add)
    }

    fun insertRecently() {
        productLiveData.value?.let {
            insertRecentlyProduct
                .execute(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .run(compositeDisposable::add)
        }
    }

    fun onFavouriteClick() {
        _productLiveData.value?.let {
            if (it.isFavourite) {
                deleteFavouriteFromPDP
                    .execute(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
                    .run(compositeDisposable::add)
                _productLiveData.value = it.copy(isFavourite = false)
            } else {
                insertFavourite
                    .execute(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
                    .run(compositeDisposable::add)
                _productLiveData.value = it.copy(isFavourite = true)
            }
        }
    }

    val adapter by lazy {
        RecentlyProductRecyclerAdapter().apply {
            setOnProductListener(object : RecentlyProductRecyclerAdapter.OnProductListener {
                override fun onProductListener(product: RecentlyProduct) {
                    recentlyProduct = product
                    _onProductClickedLiveData.call()
                }
            })
        }
    }
}
