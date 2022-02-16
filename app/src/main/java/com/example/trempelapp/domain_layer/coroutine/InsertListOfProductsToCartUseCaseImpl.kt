package com.example.trempelapp.domain_layer.coroutine

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.CartRepository
import com.example.trempelapp.utils.toCartDB
import javax.inject.Inject

class InsertListOfProductsToCartUseCaseImpl @Inject constructor(
    private val repository: CartRepository,
) : UseCase<List<Product>, Unit> {

    override suspend fun execute(params: List<Product>) {
        return repository
            .insertListOfProductsToCart(params.map { it.toCartDB() })
    }
}
