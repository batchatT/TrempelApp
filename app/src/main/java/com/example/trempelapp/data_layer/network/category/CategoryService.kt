package com.example.trempelapp.data_layer.network.category

import io.reactivex.Single
import retrofit2.http.GET

interface CategoryService {
    @GET("/products/categories")
    fun fetchCategories(): Single<List<String>>
}
