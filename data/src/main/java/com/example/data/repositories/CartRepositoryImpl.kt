package com.example.data.repositories

import android.database.sqlite.SQLiteConstraintException
import com.example.data.data_base.recently_products_data_base.TrempelDataBase
import com.example.domain_layer.models.CartDB
import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.CartRepository
import com.example.domain_layer.toProductMainModel
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val dataBase: TrempelDataBase,
) : CartRepository {

    override suspend fun getAllProducts(): List<ProductMainModel> {
        return dataBase
            .cartDao()
            .getAllItemsFromCart()
            .map {
                it.toProductMainModel()
            }
    }

    override suspend fun insertToCart(product: CartDB) {
        try {
            dataBase.cartDao().insertToCart(product)
        } catch (exception: SQLiteConstraintException) {
            dataBase.cartDao().updateItem(product.id)
        }
    }

    override suspend fun insertListOfProductsToCart(products: List<CartDB>) {
        dataBase.cartDao().insertListOfProductsToCart(products)
    }

    override suspend fun deleteItemFromCart(product: CartDB) {
        dataBase.cartDao().deleteItemFromCart(product)
    }

    override suspend fun clearCartTable() {
        dataBase.cartDao().clearCartTable()
    }
}
