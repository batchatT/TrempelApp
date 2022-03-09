package com.example.data.network.category

import io.reactivex.Single
import retrofit2.http.GET

interface CategoryService {
    @GET("/products/categories")
    fun fetchCategories(): Single<List<String>>
}
