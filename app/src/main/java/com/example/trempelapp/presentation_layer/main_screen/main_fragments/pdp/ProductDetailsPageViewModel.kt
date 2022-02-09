package com.example.trempelapp.presentation_layer.main_screen.main_fragments.pdp

import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.domain_layer.DeleteFavouriteUseCaseImpl
import com.example.trempelapp.domain_layer.FindProductByIdUseCaseImpl
import com.example.trempelapp.domain_layer.GetAllRecentlyProductsUseCaseImpl
import com.example.trempelapp.domain_layer.InsertFavouriteUseCaseImpl
import com.example.trempelapp.domain_layer.InsertRecentlyProductUseCaseImpl
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductDetailsPageViewModel @Inject constructor(
    private val getAllRecentlyProducts: GetAllRecentlyProductsUseCaseImpl,
    private val insertRecentlyProduct: InsertRecentlyProductUseCaseImpl,
    private val getProductById: FindProductByIdUseCaseImpl,
    private val insertFavourite: InsertFavouriteUseCaseImpl,
    private val deleteFavourite: DeleteFavouriteUseCaseImpl
) : BaseViewModel() {

    val onProductClickedLiveData: SingleLiveEvent<Void>
        get() = _onProductClickedLiveData
    private val _onProductClickedLiveData = SingleLiveEvent<Void>()

    val recentlyProductListLiveData: SingleLiveEvent<List<Product>>
        get() = _recentlyProductListLiveData
    private val _recentlyProductListLiveData = SingleLiveEvent<List<Product>>()

    val productLiveData: SingleLiveEvent<Product>
        get() = _productLiveData
    private val _productLiveData = SingleLiveEvent<Product>()

    var recentlyProduct: Product? = null

    private fun loadRecentlyProducts(id: Int): Single<List<Product>> {
        return getAllRecentlyProducts
            .execute(id)
    }

    fun findProductById(id: Int) {
        getProductById
            .execute(id)
            .map {
                _productLiveData.postValue(it)
                return@map it.id
            }
            .delay(100, TimeUnit.MILLISECONDS)
            .flatMap { recentlySingle(it) }
            .doOnSubscribe {
                isLoadingLiveData.postValue(true)
            }
            .doFinally {
                isLoadingLiveData.postValue(false)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _recentlyProductListLiveData.value = it
            }, {
                handleError(it)
            }).run(compositeDisposable::add)
    }

    private fun recentlySingle(id: Int): Single<List<Product>> {
        return Single.zip(
            loadRecentlyProducts(id),
            insertRecently()
                ?.andThen(Single.just(0))
        ) {
            list, _ ->
            return@zip list
        }
    }

    fun insertRecently(): Completable? {
        return productLiveData.value?.let {
            insertRecentlyProduct
                .execute(it)
        }
    }

    fun onFavouriteClick() {
        _productLiveData.value?.let {
            if (it.isFavourite) {
                deleteFavourite
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
                override fun onProductListener(product: Product) {
                    recentlyProduct = product
                    _onProductClickedLiveData.call()
                }
            })
        }
    }
}
