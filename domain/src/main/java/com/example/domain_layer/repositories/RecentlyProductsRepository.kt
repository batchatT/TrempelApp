package com.example.domain_layer.repositories

import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.models.RecentlyProductDB
import io.reactivex.Completable
import io.reactivex.Single

interface RecentlyProductsRepository {

    fun getAllRecentlyProducts(id: Int): Single<List<ProductMainModel>>

    fun insertRecentlyProduct(product: RecentlyProductDB): Single<Int>

    fun deleteItem(): Completable

    suspend fun clearRecentlyTable()
}
