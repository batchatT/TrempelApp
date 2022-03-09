package com.example.domain_layer.repositories

import com.example.domain_layer.models.ProductMainModel
import io.reactivex.Single

interface ProductRepository {

    fun fetchAllProducts(): Single<List<ProductMainModel>>

    fun fetchProductsByCategory(categoryTitle: String): Single<List<ProductMainModel>>

    fun fetchProductById(productId: Int): Single<ProductMainModel>

    fun fetchAllCategories(): Single<List<String>>
}
