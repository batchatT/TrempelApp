package com.example.trempelapp.data_layer.repositories

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.CartDB
import com.example.trempelapp.data_layer.models.Product

interface CartRepository {

    suspend fun getAllProducts(): List<Product>

    suspend fun insertToCart(product: CartDB)

    suspend fun insertListOfProductsToCart(products: List<CartDB>)

    suspend fun deleteItemFromCart(product: CartDB)

    suspend fun clearCartTable()
}
