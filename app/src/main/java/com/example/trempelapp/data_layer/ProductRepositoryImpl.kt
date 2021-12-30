package com.example.trempelapp.data_layer

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.network.product.ProductService
import io.reactivex.Single
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
) : ProductRepository {

    override fun fetchAllProducts(): Single<List<Product>> {
        TODO()
    }
}
