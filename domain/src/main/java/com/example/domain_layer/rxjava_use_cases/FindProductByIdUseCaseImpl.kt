package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.FavouritesRepository
import com.example.domain_layer.repositories.ProductRepository
import io.reactivex.Single
import javax.inject.Inject

class FindProductByIdUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
    private val favouriteRepository: FavouritesRepository
) : UseCase<Int, Single<ProductMainModel>> {

    override fun execute(params: Int): Single<ProductMainModel> {
        return Single.zip(
            productRepository.fetchProductById(params),
            favouriteRepository.isProductFavourite(params)
        ) { product, isFavourite ->
            ProductMainModel(
                id = product.id,
                title = product.title,
                price = product.price,
                description = product.description,
                imageURL = product.imageURL,
                rating = product.rating,
                category = product.category,
                isFavourite = isFavourite
            )
        }
    }
}
