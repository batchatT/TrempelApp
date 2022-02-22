package com.example.trempelapp.utils

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.CartDB
import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.FavouriteDB
import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.RecentlyProductDB
import com.example.trempelapp.data_layer.models.Product

fun Product.toFavouriteDB(): FavouriteDB {
    return FavouriteDB(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL
    )
}

fun FavouriteDB.toProduct(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL,
        description = null,
        rating = null
    )
}

fun RecentlyProductDB.toProduct(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        description = null,
        imageURL = this.imageURL,
        rating = null
    )
}

fun Product.toRecentlyProductDB(): RecentlyProductDB {
    return RecentlyProductDB(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL,
        timestamp = System.currentTimeMillis()
    )
}

fun CartDB.toProduct(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL,
        count = this.count,
        description = null,
        rating = null
    )
}

fun Product.toCartDB(): CartDB {
    return CartDB(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL,
        count = this.count
    )
}
