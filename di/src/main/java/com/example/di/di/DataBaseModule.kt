package com.example.di.di

import android.content.Context
import com.example.data.data_base.DataBase
import com.example.data.data_base.recently_products_data_base.TrempelDataBase
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
