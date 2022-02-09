package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.RecentlyProductsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAllRecentlyProductsUseCaseImpl @Inject constructor(
    private val repository: RecentlyProductsRepository
) : UseCase<Int, Single<List<Product>>> {

    override fun execute(params: Int): Single<List<Product>> {
        return repository.getAllRecentlyProducts(params)
    }
}
