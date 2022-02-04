package com.example.trempelapp.data_layer.repositories

import com.example.trempelapp.data_layer.models.Product
import io.reactivex.Single

interface ProductRepository {

    fun fetchAllProducts(): Single<List<Product>>

    fun fetchProductsByCategory(categoryTitle: String): Single<List<Product>>

    fun fetchProductById(productId: Int): Single<Product>

    fun fetchAllCategories(): Single<List<String>>
}
