package com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecentlyProductDB::class, FavouriteDB::class], version = 1)
abstract class TrempelDataBase : RoomDatabase() {
    abstract fun productDao(): RecentlyProductDAO
    abstract fun favouritesDao(): FavouritesDAO
}
