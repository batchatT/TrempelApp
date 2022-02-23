package com.example.domain_layer.coroutine_use_cases

import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.CartRepository
import javax.inject.Inject

class GetAllProductsFromCartUseCaseImpl @Inject constructor(
    private val repository: CartRepository
) : UseCase<Nothing?, List<ProductMainModel>> {

    override suspend fun execute(params: Nothing?): List<ProductMainModel> {
        return repository.getAllProducts()
    }
}
