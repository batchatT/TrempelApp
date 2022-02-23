package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.FavouritesRepository
import com.example.domain_layer.repositories.ProductRepository
import com.example.trempelapp.utils.CATEGORY_ALL
import io.reactivex.Single
import javax.inject.Inject

class FindProductsByCategoryUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
    private val favouritesRepository: FavouritesRepository,
) : UseCase<String, Single<List<ProductMainModel>>> {

    override fun execute(params: String): Single<List<ProductMainModel>> {

        return when (params) {
            CATEGORY_ALL -> Single.zip(
                productRepository.fetchAllProducts(),
                favouritesRepository.fetchAllFavourites()
            ) { products, favourites ->
                products.map { product ->
                    ProductMainModel(
                        id = product.id,
                        title = product.title,
                        price = product.price,
                        description = product.description,
                        imageURL = product.imageURL,
                        category = product.category,
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
                        ProductMainModel(
                            id = product.id,
                            title = product.title,
                            price = product.price,
                            description = product.description,
                            imageURL = product.imageURL,
                            rating = product.rating,
                            category = product.category,
                            isFavourite = favourites.any { it.id == product.id }
                        )
                    }
                }
        }
    }
}
