package com.example.trempelapp.domain_layer.coroutine

import com.example.trempelapp.data_layer.repositories.FavouritesRepository
import javax.inject.Inject

class DeleteAllDataFromFavouriteDBUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<Nothing?, Unit> {

    override suspend fun execute(params: Nothing?) {
        repository.clearFavouritesTable()
    }
}
