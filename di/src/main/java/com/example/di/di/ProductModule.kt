package com.example.di.di

import com.example.data.network.Retrofit
import com.example.data.network.category.CategoryService
import com.example.data.network.product.ProductService
import com.example.data.repositories.ProductRepositoryImpl
import com.example.domain_layer.repositories.ProductRepository
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
