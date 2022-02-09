package com.example.trempelapp.data_layer.in_memory.data_bases

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object DataBase : DataBaseProvider {

    override fun <T : RoomDatabase> provideDataBase(dataBase: Class<T>, context: Context): T {
        return Room.databaseBuilder(
            context,
            dataBase,
            dataBase::class.java.simpleName
        ).build()
    }
}
