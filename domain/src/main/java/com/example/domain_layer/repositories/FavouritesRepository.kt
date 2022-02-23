package com.example.domain_layer.repositories

import androidx.lifecycle.LiveData
import com.example.domain_layer.models.FavouriteDB
import com.example.domain_layer.models.ProductMainModel
import io.reactivex.Completable
import io.reactivex.Single

interface FavouritesRepository {

    fun fetchAllFavourites(): Single<List<ProductMainModel>>

    fun isProductFavourite(id: Int): Single<Boolean>

    fun insertFavourite(favouriteDB: FavouriteDB): Completable

    fun fetchAllFavouritesLiveData(): LiveData<List<ProductMainModel>>

    suspend fun deleteListOfFavouritesFromDB(favouriteDB: List<FavouriteDB>)

    fun deleteFavourite(favouriteDB: FavouriteDB): Completable

    fun updateFavourites(favouriteDBS: List<FavouriteDB>): Completable

    suspend fun clearFavouritesTable()
}
