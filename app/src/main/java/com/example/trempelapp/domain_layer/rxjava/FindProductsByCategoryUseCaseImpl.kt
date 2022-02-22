package com.example.trempelapp.domain_layer.rxjava

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.FavouritesRepository
import com.example.trempelapp.data_layer.repositories.ProductRepository
import com.example.trempelapp.utils.CATEGORY_ALL
import io.reactivex.Single
import javax.inject.Inject

class FindProductsByCategoryUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
    private val favouritesRepository: FavouritesRepository,
) : UseCase<String, Single<List<Product>>> {

    override fun execute(params: String): Single<List<Product>> {

        return when (params) {
            CATEGORY_ALL -> Single.zip(
                productRepository.fetchAllProducts(),
                favouritesRepository.fetchAllFavourites()
            ) { products, favourites ->
                products.map { product ->
                    Product(
                        id = product.id,
                        title = product.title,
                        price = product.price,
                        description = product.description,
                        imageURL = product.imageURL,
                        rating = product.rating,
                        isFavourite = favourites.any { it.id == product.id }
                    )
                }
            }
            else ->
                Single.zip(
                    productRepository.fetchProductsByCategory(params),
                    favouritesRepository.fetchAllFavourites()
                ) { products, favourites ->
                    products.map { product ->
                        Product(
                            id = product.id,
                            title = product.title,
                            price = product.price,
                            description = product.description,
                            imageURL = product.imageURL,
                            rating = product.rating,
                            isFavourite = favourites.any { it.id == product.id }
                        )
                    }
                }
        }
    }
}
