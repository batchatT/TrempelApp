package com.example.domain_layer.coroutine_use_cases

import com.example.domain_layer.repositories.FavouritesRepository
import javax.inject.Inject

class DeleteAllDataFromFavouriteDBUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<Nothing?, Unit> {

    override suspend fun execute(params: Nothing?) {
        repository.clearFavouritesTable()
    }
}
