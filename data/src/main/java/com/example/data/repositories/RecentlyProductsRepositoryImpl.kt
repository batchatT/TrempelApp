package com.example.data.repositories

import com.example.data.data_base.recently_products_data_base.TrempelDataBase
import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.models.RecentlyProductDB
import com.example.domain_layer.repositories.RecentlyProductsRepository
import com.example.domain_layer.toProductMainModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RecentlyProductsRepositoryImpl @Inject constructor(
    private val dataBase: TrempelDataBase,
) : RecentlyProductsRepository {

    override fun getAllRecentlyProducts(id: Int): Single<List<ProductMainModel>> {
        return dataBase.recentlyProductDao().getAll(id)
            .map { list ->
                list.map { item ->
                    item.toProductMainModel()
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
