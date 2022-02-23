package com.example.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.data.data_base.recently_products_data_base.TrempelDataBase
import com.example.domain_layer.models.FavouriteDB
import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.FavouritesRepository
import com.example.domain_layer.toProductMainModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val dataBase: TrempelDataBase,
) : FavouritesRepository {

    override fun fetchAllFavourites(): Single<List<ProductMainModel>> {
        return dataBase
            .favouritesDao()
            .getAllFavourites()
            .map { list ->
                list
                    .map { item ->
                        item.toProductMainModel()
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

    override fun fetchAllFavouritesLiveData(): LiveData<List<ProductMainModel>> {
        return Transformations.map(dataBase.favouritesDao().getAllFavouritesLiveData()) { productList ->
            productList.map {
                product ->
                product.toProductMainModel()
            }
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
