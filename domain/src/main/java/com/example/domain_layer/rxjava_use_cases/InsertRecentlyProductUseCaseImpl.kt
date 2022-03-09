package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.RecentlyProductsRepository
import com.example.domain_layer.toRecentlyProductDB
import com.example.trempelapp.utils.MAX_COUNT_OF_RECENTLY_PRODUCTS
import io.reactivex.Completable
import javax.inject.Inject

class InsertRecentlyProductUseCaseImpl @Inject constructor(
    private val repository: RecentlyProductsRepository,
) : UseCase<ProductMainModel, Completable> {

    override fun execute(params: ProductMainModel): Completable {
        return repository
            .insertRecentlyProduct(params.toRecentlyProductDB())
            .filter {
                it == MAX_COUNT_OF_RECENTLY_PRODUCTS
            }
            .flatMapCompletable {
                repository.deleteItem()
            }
    }
}
