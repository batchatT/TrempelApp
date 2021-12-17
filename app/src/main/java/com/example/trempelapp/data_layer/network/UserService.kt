package com.example.trempelapp.data_layer.network

import com.example.trempelapp.data_layer.models.auth.UserCredentials
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("/auth/login")
    @FormUrlEncoded
    fun loginUser(@Field("username")login: String, @Field("password")password: String): Single<LoginResponse>
}
