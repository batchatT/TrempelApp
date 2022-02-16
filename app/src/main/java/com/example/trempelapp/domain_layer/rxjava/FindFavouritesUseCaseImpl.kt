package com.example.trempelapp.domain_layer.rxjava

import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.data_layer.repositories.FavouritesRepository
import io.reactivex.Single
import javax.inject.Inject

class FindFavouritesUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository
) : UseCase<Nothing?, Single<List<Product>>> {

    override fun execute(params: Nothing?): Single<List<Product>> {
        return repository.fetchAllFavourites()
    }
}
