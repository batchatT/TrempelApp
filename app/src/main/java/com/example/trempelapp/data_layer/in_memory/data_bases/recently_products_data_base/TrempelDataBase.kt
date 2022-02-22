package com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecentlyProductDB::class, FavouriteDB::class, CartDB::class], version = 1)
abstract class TrempelDataBase : RoomDatabase() {
    abstract fun recentlyProductDao(): RecentlyProductDao
    abstract fun favouritesDao(): FavouritesDao
    abstract fun cartDao(): CartDao
}
