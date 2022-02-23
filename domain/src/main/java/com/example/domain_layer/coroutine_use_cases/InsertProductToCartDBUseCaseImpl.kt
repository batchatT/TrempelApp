package com.example.domain_layer.coroutine_use_cases

import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.CartRepository
import com.example.domain_layer.toCartDB
import javax.inject.Inject

class InsertProductToCartDBUseCaseImpl @Inject constructor(
    private val repository: CartRepository
) : UseCase<ProductMainModel, Unit> {

    override suspend fun execute(params: ProductMainModel) {
        repository.insertToCart(params.toCartDB())
    }
}
