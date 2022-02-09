package com.example.trempelapp.data_layer.repositories

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.FavouriteDB
import com.example.trempelapp.data_layer.models.Product
import io.reactivex.Completable
import io.reactivex.Single

interface FavouritesRepository {

    fun fetchAllFavourites(): Single<List<Product>>

    fun isProductFavourite(id: Int): Single<Boolean>

    fun insertFavourite(favouriteDB: FavouriteDB): Completable

    fun deleteFavourites(favouriteDB: FavouriteDB): Completable

    fun updateFavourites(favouriteDBS: List<FavouriteDB>): Completable
}
