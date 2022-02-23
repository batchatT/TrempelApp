package com.example.data.data_base.recently_products_data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.domain_layer.models.CartDB

@Dao
interface CartDao {

    @Query("SELECT * FROM cartdb")
    suspend fun getAllItemsFromCart(): List<CartDB>

    @Query("DELETE FROM cartdb")
    suspend fun clearCartTable()

    @Insert(onConflict = ABORT)
    suspend fun insertToCart(products: CartDB)

    @Query("UPDATE cartdb SET count = count + 1 WHERE id = :id AND count < 10")
    suspend fun updateItem(id: Int)

    @Insert(onConflict = REPLACE)
    suspend fun insertListOfProductsToCart(products: List<CartDB>)

    @Delete
    suspend fun deleteItemFromCart(products: CartDB)
}
