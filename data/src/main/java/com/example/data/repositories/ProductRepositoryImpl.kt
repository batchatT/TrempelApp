package com.example.data.repositories

import com.example.data.network.category.CategoryService
import com.example.data.network.product.ProductService
import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.ProductRepository
import com.example.domain_layer.toProductMainModel
import io.reactivex.Single
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val categoryService: CategoryService,
) : ProductRepository {

    override fun fetchAllProducts(): Single<List<ProductMainModel>> {
        return productService.fetchAllProducts().map { it.map { it.toProductMainModel() } }
    }

    override fun fetchProductsByCategory(categoryTitle: String): Single<List<ProductMainModel>> {
        return productService.fetchProductsByCategory(categoryTitle).map { it.map { it.toProductMainModel() } }
    }

    override fun fetchProductById(productId: Int): Single<ProductMainModel> {
        return productService.fetchProductsById(productId).map { it.toProductMainModel() }
    }

    override fun fetchAllCategories(): Single<List<String>> {
        return categoryService.fetchCategories()
    }
}
