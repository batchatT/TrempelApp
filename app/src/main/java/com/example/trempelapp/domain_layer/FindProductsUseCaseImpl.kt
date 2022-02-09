package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.ProductRepository
import io.reactivex.Single
import javax.inject.Inject

class FindProductsUseCaseImpl @Inject constructor(
    private val repository: ProductRepository,
) : UseCase<String, Single<List<Product>>> {

    override fun execute(params: String): Single<List<Product>> {
        TODO()
    }
}
