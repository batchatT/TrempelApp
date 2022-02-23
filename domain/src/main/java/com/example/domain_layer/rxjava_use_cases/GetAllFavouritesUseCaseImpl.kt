package com.example.domain_layer.rxjava_use_cases

import androidx.lifecycle.LiveData
import com.example.domain_layer.models.ProductMainModel
import com.example.domain_layer.repositories.FavouritesRepository
import javax.inject.Inject

class GetAllFavouritesUseCaseImpl @Inject constructor(
    private val repository: FavouritesRepository,
) : UseCase<Nothing?, LiveData<List<ProductMainModel>>> {

    override fun execute(params: Nothing?): LiveData<List<ProductMainModel>> {
        return repository.fetchAllFavouritesLiveData()
    }
}
