package com.example.trempelapp.data_layer.network

import com.example.trempelapp.data_layer.models.auth.UserCredentials
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

  @POST("/auth/login")
  fun loginUser(@Body userCredentials: UserCredentials): Single<LoginResponse>
}
