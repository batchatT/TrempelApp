package com.example.trempelapp.data_layer

import com.example.trempelapp.data_layer.models.Product
import io.reactivex.Single

interface ProductRepository {

    fun fetchAllProducts(): Single<List<Product>>

    fun fetchProductsByCategory(categoryTitle: String): Single<List<Product>>

    fun fetchAllCategories(): Single<List<String>>
}
