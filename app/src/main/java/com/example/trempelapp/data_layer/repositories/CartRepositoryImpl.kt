package com.example.trempelapp.data_layer.repositories

import android.database.sqlite.SQLiteConstraintException
import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.CartDB
import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.TrempelDataBase
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.utils.toProduct
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val dataBase: TrempelDataBase,
) : CartRepository {

    override suspend fun getAllProducts(): List<Product> {
        return dataBase
            .cartDao()
            .getAllItemsFromCart()
            .map {
                it.toProduct()
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
