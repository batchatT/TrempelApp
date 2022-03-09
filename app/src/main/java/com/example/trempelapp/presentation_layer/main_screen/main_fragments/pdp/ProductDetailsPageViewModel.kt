package com.example.trempelapp.presentation_layer.main_screen.main_fragments.pdp

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.coroutine_use_cases.InsertProductToCartDBUseCaseImpl
import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.rxjava_use_cases.DeleteFavouriteUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.FindProductByIdUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.GetAllFavouritesUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.GetAllRecentlyProductsUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.InsertFavouriteUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.InsertRecentlyProductUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.execute
import com.example.trempelapp.BaseViewModel
import com.example.utils.SingleLiveEvent
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
    private val fetchAllFavourites: GetAllFavouritesUseCaseImpl
) : BaseViewModel() {

    val onAddToCartClicked = SingleLiveEvent<Void>()

    val onProductClickedLiveData: SingleLiveEvent<ProductMainModel>
        get() = _onProductClickedLiveData
    private val _onProductClickedLiveData = SingleLiveEvent<ProductMainModel>()

    val recentlyProductListLiveData: SingleLiveEvent<List<ProductMainModel>>
        get() = _recentlyProductListLiveData
    private val _recentlyProductListLiveData = SingleLiveEvent<List<ProductMainModel>>()

    val productLiveData: SingleLiveEvent<ProductMainModel>
        get() = _productLiveData
    private val _productLiveData = SingleLiveEvent<ProductMainModel>()

    val favouritesListLiveData: LiveData<List<ProductMainModel>>
        get() = _favouritesListLiveData
    private val _favouritesListLiveData = fetchAllFavourites.execute()

    private var currentProduct: ProductMainModel? = null

    var currentProductId: Int? = null

    private fun loadRecentlyProducts(id: Int): Single<List<ProductMainModel>> {
        return getAllRecentlyProducts
            .execute(id)
    }

    fun checkFavourites() {
        val favouriteIds = _favouritesListLiveData.value?.map {
            it.id
        }
        favouriteIds?.let {
            val isFavourite = favouriteIds.contains(currentProductId)
            _productLiveData.value = productLiveData.value?.copy(isFavourite = isFavourite)
        }
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

    private fun recentlySingle(id: Int): Single<List<ProductMainModel>> {
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
                override fun onProductListener(product: ProductMainModel) {
                    _onProductClickedLiveData.value = product
                }
            })
        }
    }
}
