package com.example.trempelapp.di

import com.example.trempelapp.data_layer.ProductRepository
import com.example.trempelapp.data_layer.ProductRepositoryImpl
import com.example.trempelapp.data_layer.network.Retrofit
import com.example.trempelapp.data_layer.network.product.ProductService
import dagger.Module
import dagger.Provides

@Module
class ProductModule {

    @Provides
    fun provideProductRepository(repository: ProductRepositoryImpl): ProductRepository {
        return repository
    }

    @Provides
    fun provideRetrofit(retrofit: Retrofit): ProductService {
        return retrofit.provideService(ProductService::class.java)
    }
}
