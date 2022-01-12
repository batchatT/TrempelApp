package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.ProductRepository
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.utils.CATEGORY_ALL
import io.reactivex.Single
import javax.inject.Inject

class FindProductsByCategoryUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : UseCase<String, Single<List<Product>>> {

    override fun execute(params: String): Single<List<Product>> {
        return when (params) {
            CATEGORY_ALL -> repository.fetchAllProducts()
            else -> repository.fetchProductsByCategory(params)
        }
    }
}
