package com.example.trempelapp.data_layer.repositories

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.RecentlyProduct
import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.TrempelDataBase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RecentlyProductsRepositoryImpl @Inject constructor(
    private val dataBase: TrempelDataBase,
) : RecentlyProductsRepository {

    override fun getAllRecentlyProducts(id: Int): Single<List<RecentlyProduct>> {
        return dataBase.productDao().getAll(id)
    }

    override fun insertRecentlyProduct(product: RecentlyProduct): Single<Int> {
        return Completable.fromAction {
            dataBase.productDao().insertProduct(product)
        }
            .andThen(getCount())
    }

    private fun getCount(): Single<Int> {
        return dataBase
            .productDao()
            .getCountOfRows()
    }

    override fun deleteItem(): Completable {
        return Completable
            .fromAction {
                dataBase.productDao().deleteTheLatest()
            }
    }
}
