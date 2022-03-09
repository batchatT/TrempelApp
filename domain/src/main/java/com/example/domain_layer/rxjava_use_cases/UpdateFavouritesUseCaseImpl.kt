package com.example.domain_layer.rxjava_use_cases

import com.example.domain_layer.models.FavouriteDB
import com.example.domain_layer.repositories.FavouritesRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateFavouritesUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<List<FavouriteDB>, Completable> {

    override fun execute(params: List<FavouriteDB>): Completable {
        return repository.updateFavourites(params)
    }
}
