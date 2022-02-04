package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.RecentlyProduct
import com.example.trempelapp.data_layer.repositories.RecentlyProductsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAllRecentlyProductsUseCaseImpl @Inject constructor(
    private val repository: RecentlyProductsRepository
) : UseCase<Int, Single<List<RecentlyProduct>>> {

    override fun execute(params: Int): Single<List<RecentlyProduct>> {
        return repository.getAllRecentlyProducts(params)
    }
}
