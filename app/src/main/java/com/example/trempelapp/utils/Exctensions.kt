package com.example.trempelapp.utils

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.FavouriteDB
import com.example.trempelapp.data_layer.models.Favourite
import com.example.trempelapp.data_layer.models.Product

fun Product.toFavouriteDB(): FavouriteDB {
    return FavouriteDB(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL
    )
}

fun Favourite.toFavouriteDB(): FavouriteDB {
    return FavouriteDB(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL,
        count = this.count
    )
}

fun FavouriteDB.toFavourite(): Favourite {
    return Favourite(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL,
        count = this.count
    )
}
