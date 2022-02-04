package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.models.Favourite
import com.example.trempelapp.data_layer.repositories.FavouritesRepository
import io.reactivex.Single
import javax.inject.Inject

class FindFavouritesUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<Nothing?, Single<List<Favourite>>> {

    override fun execute(params: Nothing?): Single<List<Favourite>> {
        return repository.fetchAllFavourites()
    }
}
