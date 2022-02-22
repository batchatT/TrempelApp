package com.example.trempelapp.data_layer.repositories

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.RecentlyProductDB
import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.TrempelDataBase
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.utils.toProduct
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RecentlyProductsRepositoryImpl @Inject constructor(
    private val dataBase: TrempelDataBase,
) : RecentlyProductsRepository {

    override fun getAllRecentlyProducts(id: Int): Single<List<Product>> {
        return dataBase.recentlyProductDao().getAll(id)
            .map { list ->
                list.map { item ->
                    item.toProduct()
                }
            }
    }

    override fun insertRecentlyProduct(product: RecentlyProductDB): Single<Int> {
        return Completable.fromAction {
            dataBase.recentlyProductDao().insertProduct(product)
        }
            .andThen(getCount())
    }

    private fun getCount(): Single<Int> {
        return dataBase
            .recentlyProductDao()
            .getCountOfRows()
    }

    override fun deleteItem(): Completable {
        return Completable
            .fromAction {
                dataBase.recentlyProductDao().deleteTheLatest()
            }
    }

    override suspend fun clearRecentlyTable() {
        dataBase.recentlyProductDao().clearRecentlyProductsTable()
    }
}
