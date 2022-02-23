package com.example.domain_layer.coroutine_use_cases

import com.example.domain_layer.repositories.RecentlyProductsRepository
import javax.inject.Inject

class DeleteAllDataFromRecentlyProductsDBUseCaseImpl @Inject constructor(
    private val repository: RecentlyProductsRepository,
) : UseCase<Nothing?, Unit> {

    override suspend fun execute(params: Nothing?) {
        repository.clearRecentlyTable()
    }
}
