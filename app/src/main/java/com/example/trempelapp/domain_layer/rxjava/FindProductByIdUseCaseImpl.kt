package com.example.trempelapp.domain_layer.rxjava

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.FavouritesRepository
import com.example.trempelapp.data_layer.repositories.ProductRepository
import io.reactivex.Single
import javax.inject.Inject

class FindProductByIdUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
    private val favouriteRepository: FavouritesRepository
) : UseCase<Int, Single<Product>> {

    override fun execute(params: Int): Single<Product> {
        return Single.zip(
            productRepository.fetchProductById(params),
            favouriteRepository.isProductFavourite(params)
        ) { product, isFavourite ->
            Product(
                id = product.id,
                title = product.title,
                price = product.price,
                description = product.description,
                imageURL = product.imageURL,
                rating = product.rating,
                isFavourite = isFavourite
            )
        }
    }
}
