package com.example.data.data_base.recently_products_data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain_layer.models.CartDB
import com.example.domain_layer.models.FavouriteDB
import com.example.domain_layer.models.RecentlyProductDB

@Database(entities = [RecentlyProductDB::class, FavouriteDB::class, CartDB::class], version = 1)
abstract class TrempelDataBase : RoomDatabase() {
    abstract fun recentlyProductDao(): RecentlyProductDao
    abstract fun favouritesDao(): FavouritesDao
    abstract fun cartDao(): CartDao
}
