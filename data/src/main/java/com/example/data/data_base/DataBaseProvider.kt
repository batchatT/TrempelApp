package com.example.data.data_base

import android.content.Context
import androidx.room.RoomDatabase

interface DataBaseProvider {

    fun <T : RoomDatabase> provideDataBase(dataBase: Class<T>, context: Context): T
}
