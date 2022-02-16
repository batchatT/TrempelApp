package com.example.trempelapp.data_layer.repositories

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.RecentlyProductDB
import com.example.trempelapp.data_layer.models.Product
import io.reactivex.Completable
import io.reactivex.Single

interface RecentlyProductsRepository {

    fun getAllRecentlyProducts(id: Int): Single<List<Product>>

    fun insertRecentlyProduct(product: RecentlyProductDB): Single<Int>

    fun deleteItem(): Completable

    suspend fun clearRecentlyTable()
}
