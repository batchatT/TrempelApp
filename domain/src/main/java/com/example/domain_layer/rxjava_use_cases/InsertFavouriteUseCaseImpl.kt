package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.FavouritesRepository
import com.example.domain_layer.toFavouriteDB
import io.reactivex.Completable
import javax.inject.Inject

class InsertFavouriteUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<ProductMainModel, Completable> {

    override fun execute(params: ProductMainModel): Completable {
        return repository.insertFavourite(params.toFavouriteDB())
    }
}
