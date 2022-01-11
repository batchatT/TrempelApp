package com.example.trempelapp.data_layer.network.login

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    @POST("/auth/login")
    @FormUrlEncoded
    fun loginUser(@Field("username") login: String, @Field("password") password: String): Single<LoginResponse>
}
