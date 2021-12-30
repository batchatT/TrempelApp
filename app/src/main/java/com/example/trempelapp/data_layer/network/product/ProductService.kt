package com.example.trempelapp.data_layer.network.product

import com.example.trempelapp.data_layer.models.Product
import io.reactivex.Single
import retrofit2.http.GET

interface ProductService {

    @GET("/products")
    fun fetchAllProducts(): Single<List<Product>>
}
