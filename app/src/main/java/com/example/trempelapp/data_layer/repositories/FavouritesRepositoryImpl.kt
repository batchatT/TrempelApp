package com.example.trempelapp.data_layer.repositories

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.FavouriteDB
import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.TrempelDataBase
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.utils.toProduct
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val dataBase: TrempelDataBase,
) : FavouritesRepository {

    override fun fetchAllFavourites(): Single<List<Product>> {
        return dataBase
            .favouritesDao()
            .getAllFavourites()
            .map { list ->
                list
                    .map { item ->
                        item.toProduct()
                    }
            }
    }

    override fun isProductFavourite(id: Int): Single<Boolean> {
        return dataBase.favouritesDao().getIsFavourite(id)
    }

    override fun insertFavourite(favouriteDB: FavouriteDB): Completable {
        return Completable
            .fromAction {
                dataBase.favouritesDao().insertFavourite(favouriteDB)
            }
    }

    override suspend fun deleteListOfFavouritesFromDB(favouriteDB: List<FavouriteDB>) {
        dataBase.favouritesDao().deleteListOfFavourites(favouriteDB)
    }

    override fun deleteFavourite(favouriteDB: FavouriteDB): Completable {
        return Completable
            .fromAction {
                dataBase.favouritesDao().delete(favouriteDB)
            }
    }

    override fun updateFavourites(favouriteDBS: List<FavouriteDB>): Completable {
        return Completable
            .fromAction {
                dataBase.favouritesDao().updateFavourites(favouriteDBS)
            }
    }

    override suspend fun clearFavouritesTable() {
        dataBase.favouritesDao().clearFavouriteTable()
    }
}
