package com.example.trempelapp.data_layer

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.network.category.CategoryService
import com.example.trempelapp.data_layer.network.product.ProductService
import io.reactivex.Single
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val categoryService: CategoryService,
) : ProductRepository {

    override fun fetchAllProducts(): Single<List<Product>> {
        return productService.fetchAllProducts()
    }

    override fun fetchProductsByCategory(categoryTitle: String): Single<List<Product>> {
        return productService.fetchProductsByCategory(categoryTitle)
    }

    override fun fetchAllCategories(): Single<List<String>> {
        return categoryService.fetchCategories()
    }
}
