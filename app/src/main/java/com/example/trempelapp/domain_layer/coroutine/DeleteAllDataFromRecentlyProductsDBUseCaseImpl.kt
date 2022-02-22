package com.example.trempelapp.domain_layer.coroutine

import com.example.trempelapp.data_layer.repositories.RecentlyProductsRepository
import javax.inject.Inject

class DeleteAllDataFromRecentlyProductsDBUseCaseImpl @Inject constructor(
    private val repository: RecentlyProductsRepository,
) : UseCase<Nothing?, Unit> {

    override suspend fun execute(params: Nothing?) {
        repository.clearRecentlyTable()
    }
}
