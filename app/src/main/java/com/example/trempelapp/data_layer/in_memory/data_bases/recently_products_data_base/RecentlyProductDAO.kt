package com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Single

@Dao
interface RecentlyProductDAO {
    @Query(
        """
        SELECT * FROM recentlyproductdb 
        WHERE :id <> id 
        ORDER BY timestamp DESC """
    )
    fun getAll(id: Int): Single<List<RecentlyProductDB>>

    @Insert(onConflict = REPLACE)
    fun insertProduct(product: RecentlyProductDB)

    @Query("SELECT count(id) FROM recentlyproductdb")
    fun getCountOfRows(): Single<Int>

    @Query(
        """
        DELETE FROM recentlyproductdb 
        WHERE timestamp = (SELECT min(timestamp) 
                           FROM recentlyproductdb)"""
    )
    fun deleteTheLatest()
}
