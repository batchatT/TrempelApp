package com.example.domain_layer

import com.example.domain_layer.models.CartDB
import com.example.domain_layer.models.FavouriteDB
import com.example.domain_layer.models.Product
import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.models.Rating
import com.example.domain_layer.models.RatingMainModel
import com.example.domain_layer.models.RecentlyProductDB

fun ProductMainModel.toFavouriteDB(): FavouriteDB {
    return FavouriteDB(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        imageURL = this.imageURL
    )
}

fun FavouriteDB.toProductMainModel(): ProductMainModel {
    return ProductMainModel(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL,
        description = this.description,
        category = this.category,
        rating = null,
        isFavourite = true
    )
}

fun RecentlyProductDB.toProductMainModel(): ProductMainModel {
    return ProductMainModel(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        imageURL = this.imageURL,
        category = this.category,
        rating = null
    )
}

fun ProductMainModel.toRecentlyProductDB(): RecentlyProductDB {
    return RecentlyProductDB(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL,
        description = this.description,
        category = this.category,
        timestamp = System.currentTimeMillis()
    )
}

fun CartDB.toProductMainModel(): ProductMainModel {
    return ProductMainModel(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL,
        count = this.count,
        category = this.category,
        description = this.description,
        rating = null
    )
}

fun ProductMainModel.toCartDB(): CartDB {
    return CartDB(
        id = this.id,
        title = this.title,
        price = this.price,
        imageURL = this.imageURL,
        description = this.description,
        category = this.category,
        count = this.count
    )
}

fun Product.toProductMainModel(): ProductMainModel {
    return ProductMainModel(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        imageURL = this.imageURL,
        rating = this.rating?.toRatingMainModel(),
        isFavourite = this.isFavourite,
        category = this.category,
        count = this.count,
        timestamp = this.timestamp
    )
}

fun Rating.toRatingMainModel(): RatingMainModel {
    return RatingMainModel(
        rate = this.rate,
        commentsCount = this.commentsCount
    )
}
