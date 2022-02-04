package com.example.trempelapp.data_layer.in_memory.data_bases

import android.content.Context
import androidx.room.RoomDatabase

interface DataBaseProvider {

    fun <T : RoomDatabase> provideDataBase(dataBase: Class<T>, context: Context): T
}
