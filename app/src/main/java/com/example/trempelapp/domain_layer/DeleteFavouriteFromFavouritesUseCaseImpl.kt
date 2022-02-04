package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.models.Favourite
import com.example.trempelapp.data_layer.repositories.FavouritesRepository
import com.example.trempelapp.utils.toFavouriteDB
import io.reactivex.Completable
import javax.inject.Inject

class DeleteFavouriteFromFavouritesUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<Favourite, Completable> {

    override fun execute(params: Favourite): Completable {
        return repository.deleteFavourites(params.toFavouriteDB())
    }
}
