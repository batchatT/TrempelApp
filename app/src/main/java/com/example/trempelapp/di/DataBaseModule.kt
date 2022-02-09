package com.example.trempelapp.di

import android.content.Context
import com.example.trempelapp.data_layer.in_memory.data_bases.DataBase
import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.TrempelDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideRecentlyProductsDataBase(context: Context): TrempelDataBase {
        return DataBase.provideDataBase(TrempelDataBase::class.java, context)
    }
}
