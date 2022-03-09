package com.example.domain_layer.repositories

import com.example.domain_layer.models.CartDB
import com.example.domain_layer.models.ProductMainModel

interface CartRepository {

    suspend fun getAllProducts(): List<ProductMainModel>

    suspend fun insertToCart(product: CartDB)

    suspend fun insertListOfProductsToCart(products: List<CartDB>)

    suspend fun deleteItemFromCart(product: CartDB)

    suspend fun clearCartTable()
}
