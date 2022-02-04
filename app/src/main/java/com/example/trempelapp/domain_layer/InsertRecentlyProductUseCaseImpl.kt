package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.RecentlyProduct
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.RecentlyProductsRepository
import io.reactivex.Completable
import javax.inject.Inject

class InsertRecentlyProductUseCaseImpl @Inject constructor(
    private val repository: RecentlyProductsRepository,
) : UseCase<Product, Completable> {

    override fun execute(params: Product): Completable {
        return repository
            .insertRecentlyProduct(params.toRecentlyProduct())
            .filter {
                it == 11
            }
            .flatMapCompletable {
                repository.deleteItem()
            }
    }

    private fun Product.toRecentlyProduct(): RecentlyProduct {
        return RecentlyProduct(
            id = this.id,
            title = this.title,
            price = this.price,
            imageURL = this.imageURL,
            timestamp = System.currentTimeMillis()
        )
    }
}
