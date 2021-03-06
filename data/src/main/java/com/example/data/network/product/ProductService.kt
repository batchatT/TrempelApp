package com.example.data.network.product

import com.example.domain_layer.models.Product
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("/products")
    fun fetchAllProducts(): Single<List<Product>>

    @GET("/products/category/{categoryTitle}")
    fun fetchProductsByCategory(@Path("categoryTitle") category: String): Single<List<Product>>

    @GET("/products/{id}")
    fun fetchProductsById(@Path("id") id: Int): Single<Product>
}
