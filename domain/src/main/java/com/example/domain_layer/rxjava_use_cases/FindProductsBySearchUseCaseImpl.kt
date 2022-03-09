package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.ErrorUtils
import com.example.domain_layer.SearchQueryException
import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.FavouritesRepository
import com.example.domain_layer.repositories.ProductRepository
import io.reactivex.Single
import javax.inject.Inject

class FindProductsBySearchUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
    private val favouritesRepository: FavouritesRepository,
) : UseCase<String, Single<List<ProductMainModel>>> {

    override fun execute(params: String): Single<List<ProductMainModel>> {
        val searchQueryErrorDto = ErrorUtils.checkSearchQuery(params)
        if (!searchQueryErrorDto.isSearchQueryCorrect()) {
            return Single.error(
                SearchQueryException(
                    "Search is incorrect",
                    searchQueryErrorDto.queryError
                )
            )
        }
        return Single.zip(
            productRepository.fetchAllProducts()
                .map {
                    it.filter { product ->
                        checkParameterMatch(params, product)
                    }
                },
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

    private fun checkParameterMatch(param: String, product: ProductMainModel): Boolean {
        return param.toRegex().containsMatchIn(product.title.lowercase()) ||
            param.toRegex().containsMatchIn(product.description.lowercase()) ||
            param.toRegex().containsMatchIn(product.category.lowercase())
    }
}
