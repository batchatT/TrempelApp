package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.RecentlyProductsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAllRecentlyProductsUseCaseImpl @Inject constructor(
    private val repository: RecentlyProductsRepository
) : UseCase<Int, Single<List<ProductMainModel>>> {

    override fun execute(params: Int): Single<List<ProductMainModel>> {
        return repository.getAllRecentlyProducts(params)
    }
}
