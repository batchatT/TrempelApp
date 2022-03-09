package com.example.data.data_base.recently_products_data_base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.domain_layer.models.RecentlyProductDB
import io.reactivex.Single

@Dao
interface RecentlyProductDao {
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

    @Query("DELETE FROM recentlyproductdb")
    suspend fun clearRecentlyProductsTable()
}
