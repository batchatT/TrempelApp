package com.example.trempelapp.domain_layer.coroutine

import com.example.trempelapp.data_layer.repositories.CartRepository
import com.example.trempelapp.data_layer.repositories.FavouritesRepository
import com.example.trempelapp.data_layer.repositories.RecentlyProductsRepository
import com.example.trempelapp.data_layer.repositories.UserInfoRepository
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
