package com.example.trempelapp.di

import com.example.trempelapp.data_layer.ProductRepository
import com.example.trempelapp.data_layer.ProductRepositoryImpl
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.network.Retrofit
import com.example.trempelapp.data_layer.network.category.CategoryService
import com.example.trempelapp.data_layer.network.product.ProductService
import com.example.trempelapp.domain_layer.FindProductsByCategoryUseCaseImpl
import com.example.trempelapp.domain_layer.UseCase
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import javax.inject.Named

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
