package com.example.domain_layer.coroutine_use_cases

import com.example.domain_layer.repositories.CartRepository
import com.example.domain_layer.repositories.FavouritesRepository
import com.example.domain_layer.repositories.RecentlyProductsRepository
import com.example.domain_layer.repositories.UserInfoRepository
import javax.inject.Inject

class DeleteAllDataFromCartDBUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository,
    private val favouriteRepository: FavouritesRepository,
    private val recentlyRepository: RecentlyProductsRepository,
    private val userRepository: UserInfoRepository
) : UseCase<Nothing?, Unit> {

    override suspend fun execute(params: Nothing?) {
        cartRepository.clearCartTable()
        favouriteRepository.clearFavouritesTable()
        recentlyRepository.clearRecentlyTable()
        userRepository.clearUserData()
    }
}
