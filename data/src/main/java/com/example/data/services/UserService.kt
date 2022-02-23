package com.example.data.services

import com.example.domain_layer.models.LoginResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    @POST("/auth/login")
    @FormUrlEncoded
    fun loginUser(@Field("username") login: String, @Field("password") password: String): Single<LoginResponse>
}
