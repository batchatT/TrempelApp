package com.example.trempelapp.presentation_layer.main_screen.main_fragments.pdp

import androidx.lifecycle.viewModelScope
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.domain_layer.coroutine.InsertProductToCartDBUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.DeleteFavouriteUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.FindProductByIdUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.GetAllRecentlyProductsUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.InsertFavouriteUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.InsertRecentlyProductUseCaseImpl
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsPageViewModel @Inject constructor(
    private val getAllRecentlyProducts: GetAllRecentlyProductsUseCaseImpl,
    private val insertRecentlyProduct: InsertRecentlyProductUseCaseImpl,
    private val getProductById: FindProductByIdUseCaseImpl,
    private val insertFavourite: InsertFavouriteUseCaseImpl,
    private val deleteFavourite: DeleteFavouriteUseCaseImpl,
    private val insertProductToCart: InsertProductToCartDBUseCaseImpl,
) : BaseViewModel() {

    val onAddToCartClicked = SingleLiveEvent<Void>()

    val onProductClickedLiveData: SingleLiveEvent<Product>
        get() = _onProductClickedLiveData
    private val _onProductClickedLiveData = SingleLiveEvent<Product>()

    val recentlyProductListLiveData: SingleLiveEvent<List<Product>>
        get() = _recentlyProductListLiveData
    private val _recentlyProductListLiveData = SingleLiveEvent<List<Product>>()

    val productLiveData: SingleLiveEvent<Product>
        get() = _productLiveData
    private val _productLiveData = SingleLiveEvent<Product>()

    private var currentProduct: Product? = null

    var currentProductId: Int? = null

    private fun loadRecentlyProducts(id: Int): Single<List<Product>> {
        return getAllRecentlyProducts
            .execute(id)
    }

    fun findProductById() {
        currentProductId?.let {
            getProductById
                .execute(it)
                .map { product ->
                    _productLiveData.postValue(product)
                    currentProduct = product
                    return@map product.id
                }
                .flatMap { id -> recentlySingle(id) }
                .doOnSubscribe {
                    isLoadingLiveData.postValue(true)
                }
                .doFinally {
                    isLoadingLiveData.postValue(false)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ productsList ->
                    _recentlyProductListLiveData.value = productsList
                }, { error ->
                    handleError(error)
                }).run(compositeDisposable::add)
        }
    }

    private fun recentlySingle(id: Int): Single<List<Product>> {
        return Single.zip(
            loadRecentlyProducts(id),
            insertRecently()
                ?.andThen(Single.just(0))
        ) { list, _ ->
            return@zip list
        }
    }

    fun insertRecently(): Completable? {
        return currentProduct?.let {
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

    fun onAddToCartClicked() {
        viewModelScope.launch {
            currentProduct?.let {
                insertProductToCart.execute(it)
            }
        }
        onAddToCartClicked.call()
    }

    val adapter by lazy {
        RecentlyProductRecyclerAdapter().apply {
            setOnProductListener(object : RecentlyProductRecyclerAdapter.OnProductListener {
                override fun onProductListener(product: Product) {
                    _onProductClickedLiveData.value = product
                }
            })
        }
    }
}
