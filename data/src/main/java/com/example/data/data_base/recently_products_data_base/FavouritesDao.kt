package com.example.data.data_base.recently_products_data_base

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.domain_layer.models.FavouriteDB
import io.reactivex.Single

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM favouritedb")
    fun getAllFavourites(): Single<List<FavouriteDB>>

    @Query("SELECT * FROM favouritedb")
    fun getAllFavouritesLiveData(): LiveData<List<FavouriteDB>>

    @Query("SELECT EXISTS (SELECT * FROM favouritedb WHERE id = :id )")
    fun getIsFavourite(id: Int): Single<Boolean>

    @Query("DELETE FROM favouritedb")
    suspend fun clearFavouriteTable()

    @Insert(onConflict = REPLACE)
    fun insertFavourite(vararg favouriteDB: FavouriteDB)

    @Update
    fun updateFavourites(favouriteDBS: List<FavouriteDB>)

    @Delete
    fun delete(favouriteDB: FavouriteDB)

    @Delete
    suspend fun deleteListOfFavourites(favouriteDB: List<FavouriteDB>)
}
