package com.example.trempelapp.presentation_layer.main_screen.main_fragments.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.coroutine_use_cases.DeleteProductFromCartDBUseCaseImpl
import com.example.domain_layer.coroutine_use_cases.GetAllProductsFromCartUseCaseImpl
import com.example.domain_layer.coroutine_use_cases.InsertProductToCartDBUseCaseImpl
import com.example.domain_layer.coroutine_use_cases.execute
import com.example.domain_layer.models.ProductMainModel
import com.example.trempelapp.BaseViewModel
import com.example.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartPageViewModelController @Inject constructor(
    private val getAllProducts: GetAllProductsFromCartUseCaseImpl,
    private val deleteProductFromCartDB: DeleteProductFromCartDBUseCaseImpl,
    private val insertProductToCart: InsertProductToCartDBUseCaseImpl,
) : BaseViewModel() {

    val adapter by lazy {
        CartItemRecyclerAdapter(cartItemToRemoveLiveData).apply {
            setOnProductListener(object : CartItemRecyclerAdapter.OnCartItemListener {
                override fun onCartItemListener(cartItem: CartRecyclerItem) {
                    _onProductClickedLiveData.value = cartItem.cartItem
                }
            })
        }
    }
    val itemsCountLiveData = MediatorLiveData<Int>()
    val priceLiveData = SingleLiveEvent<Float>()
    val cartItemToRemoveLiveData =
        SingleLiveEvent<CartRecyclerItem>()
    val productsListLiveData: SingleLiveEvent<List<CartRecyclerItem>>
        get() = _productsListLiveData
    val onProductClickedLiveData: SingleLiveEvent<ProductMainModel>
        get() = _onProductClickedLiveData
    private val _onProductClickedLiveData =
        SingleLiveEvent<ProductMainModel>()
    private val _productsListLiveData =
        SingleLiveEvent<List<CartRecyclerItem>>()

    val listSizeLiveData: LiveData<Int> = Transformations.map(_productsListLiveData) { it.size }

    init {
        itemsCountLiveData.addSource(_productsListLiveData) {
            calculateQuantityAndPrice()
        }
    }

    fun getAllProductsFromCart() {
        viewModelScope.launch {
            _productsListLiveData.value = getAllProducts.execute()
                .map { CartRecyclerItem(it) }
                .onEach {
                    itemsCountLiveData.addSource(it.quantityLiveData) {
                        calculateQuantityAndPrice()
                    }
                    it.isIncreasable()
                }
        }
    }

    private fun calculateQuantityAndPrice() {
        itemsCountLiveData.value = _productsListLiveData.value
            ?.mapNotNull { product ->
                product.quantityLiveData.value?.toIntOrNull()
            }?.sum()

        priceLiveData.value = _productsListLiveData.value
            ?.mapNotNull {
                it.quantityLiveData.value?.toFloat()?.times(it.cartItem.price)
            }?.sum()
    }

    fun removeCartItemFromCartDB(product: ProductMainModel) {
        viewModelScope.launch {
            deleteProductFromCartDB.execute(product)
            _productsListLiveData.postValue(
                productsListLiveData.value?.filter { it.cartItem != product }
            )
        }
    }

    fun insertProductToCartDB(position: Int) {
        viewModelScope.launch {
            cartItemToRemoveLiveData.value?.let { item ->
                insertProductToCart.execute(item.cartItem)

                val products = _productsListLiveData.value?.toMutableList()
                products?.add(position, item)
                products?.let { _productsListLiveData.postValue(it) }
            }
        }
    }
}
