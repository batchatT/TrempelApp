package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.FavouritesRepository
import io.reactivex.Single
import javax.inject.Inject

class FindFavouritesUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<Nothing?, Single<List<ProductMainModel>>> {

    override fun execute(params: Nothing?): Single<List<ProductMainModel>> {
        return repository.fetchAllFavourites()
    }
}
