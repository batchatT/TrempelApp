package com.example.trempelapp.di

import com.example.trempelapp.data_layer.network.Retrofit
import com.example.trempelapp.data_layer.network.category.CategoryService
import com.example.trempelapp.data_layer.network.product.ProductService
import com.example.trempelapp.data_layer.repositories.ProductRepository
import com.example.trempelapp.data_layer.repositories.ProductRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ProductModule {

    @Provides
    fun provideProductRepository(repository: ProductRepositoryImpl): ProductRepository {
        return repository
    }

    @Provides
    fun provideProductRetrofit(retrofit: Retrofit): ProductService {
        return retrofit.provideService(ProductService::class.java)
    }

    @Provides
    fun provideCategoriesRetrofit(retrofit: Retrofit): CategoryService {
        return retrofit.provideService(CategoryService::class.java)
    }
}
