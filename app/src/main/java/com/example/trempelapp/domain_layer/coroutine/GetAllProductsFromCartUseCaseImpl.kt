package com.example.trempelapp.domain_layer.coroutine

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.CartRepository
import javax.inject.Inject

class GetAllProductsFromCartUseCaseImpl @Inject constructor(
    private val repository: CartRepository
) : UseCase<Nothing?, List<Product>> {

    override suspend fun execute(params: Nothing?): List<Product> {
        return repository.getAllProducts()
    }
}
