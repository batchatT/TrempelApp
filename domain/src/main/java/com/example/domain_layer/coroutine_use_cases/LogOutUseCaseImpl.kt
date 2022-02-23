package com.example.domain_layer.coroutine_use_cases

import com.example.domain_layer.repositories.AuthRepository
import com.example.domain_layer.repositories.CartRepository
import com.example.domain_layer.repositories.FavouritesRepository
import com.example.domain_layer.repositories.RecentlyProductsRepository
import javax.inject.Inject

class LogOutUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository,
    private val favouritesRepository: FavouritesRepository,
    private val recentlyProductRepository: RecentlyProductsRepository,
    private val authRepository: AuthRepository

) : UseCase<Nothing?, Unit> {

    override suspend fun execute(params: Nothing?) {
        cartRepository.clearCartTable()
        favouritesRepository.clearFavouritesTable()
        recentlyProductRepository.clearRecentlyTable()
        authRepository.logOutUser()
    }
}
