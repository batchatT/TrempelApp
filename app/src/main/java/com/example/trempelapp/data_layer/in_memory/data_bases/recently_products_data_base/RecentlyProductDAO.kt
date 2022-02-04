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
        select * from recentlyproduct 
        where :id <> id 
        order by timestamp desc """
    )
    fun getAll(id: Int): Single<List<RecentlyProduct>>

    @Insert(onConflict = REPLACE)
    fun insertProduct(product: RecentlyProduct)

    @Query("select count(id) from recentlyproduct")
    fun getCountOfRows(): Single<Int>

    @Query(
        """
        delete from recentlyproduct 
        where timestamp = (select min(timestamp) 
                           from recentlyproduct)"""
    )
    fun deleteTheLatest()
}
