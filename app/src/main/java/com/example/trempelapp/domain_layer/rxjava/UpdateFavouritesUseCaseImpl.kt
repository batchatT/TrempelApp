package com.example.trempelapp.domain_layer.rxjava

import com.example.trempelapp.data_layer.in_memory.data_bases.recently_products_data_base.FavouriteDB
import com.example.trempelapp.data_layer.repositories.FavouritesRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateFavouritesUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<List<FavouriteDB>, Completable> {

    override fun execute(params: List<FavouriteDB>): Completable {
        return repository.updateFavourites(params)
    }
}
