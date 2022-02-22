package com.example.trempelapp.domain_layer.coroutine

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.CartRepository
import com.example.trempelapp.utils.toCartDB
import javax.inject.Inject

class InsertProductToCartDBUseCaseImpl @Inject constructor(
    private val repository: CartRepository
) : UseCase<Product, Unit> {

    override suspend fun execute(params: Product) {
        repository.insertToCart(params.toCartDB())
    }
}
