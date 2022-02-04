package com.example.trempelapp.data_layer.repositories

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.RecentlyProduct
import io.reactivex.Completable
import io.reactivex.Single

interface RecentlyProductsRepository {

    fun getAllRecentlyProducts(id: Int): Single<List<RecentlyProduct>>

    fun insertRecentlyProduct(product: RecentlyProduct): Single<Int>

    fun deleteItem(): Completable
}
