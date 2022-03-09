package com.example.domain_layer.coroutine_use_cases

import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.FavouritesRepository
import com.example.domain_layer.toFavouriteDB
import javax.inject.Inject

class DeleteFavouritesListFromFavouriteDBUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<List<ProductMainModel>, Unit> {

    override suspend fun execute(params: List<ProductMainModel>) {
        repository.deleteListOfFavouritesFromDB(params.map { it.toFavouriteDB() })
    }
}
