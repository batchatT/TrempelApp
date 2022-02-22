package com.example.trempelapp.domain_layer.coroutine

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.FavouritesRepository
import com.example.trempelapp.utils.toFavouriteDB
import javax.inject.Inject

class DeleteFavouritesListFromFavouriteDBUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<List<Product>, Unit> {

    override suspend fun execute(params: List<Product>) {
        repository.deleteListOfFavouritesFromDB(params.map { it.toFavouriteDB() })
    }
}
