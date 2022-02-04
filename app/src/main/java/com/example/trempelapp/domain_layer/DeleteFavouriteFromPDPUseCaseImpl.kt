package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.FavouritesRepository
import com.example.trempelapp.utils.toFavouriteDB
import io.reactivex.Completable
import javax.inject.Inject

class DeleteFavouriteFromPDPUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<Product, Completable> {

    override fun execute(params: Product): Completable {
        return repository.deleteFavourites(params.toFavouriteDB())
    }
}
