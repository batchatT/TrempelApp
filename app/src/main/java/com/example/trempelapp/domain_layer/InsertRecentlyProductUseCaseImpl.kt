package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.RecentlyProductsRepository
import com.example.trempelapp.utils.MAX_COUNT_OF_RECENTLY_PRODUCTS
import com.example.trempelapp.utils.toRecentlyProductDB
import io.reactivex.Completable
import javax.inject.Inject

class InsertRecentlyProductUseCaseImpl @Inject constructor(
    private val repository: RecentlyProductsRepository,
) : UseCase<Product, Completable> {

    override fun execute(params: Product): Completable {
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
