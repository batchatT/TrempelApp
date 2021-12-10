package com.example.trempelapp.domainLayer

import com.example.trempelapp.dataLayer.inMemory.UserInfoRepository
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(private val repository: UserInfoRepository): GetUserInfoUseCase {

     override fun invoke() = repository.getUserToken()

}
