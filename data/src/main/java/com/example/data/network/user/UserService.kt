package com.example.data.network.user

import com.example.domain_layer.models.LoginResponse
import com.example.domain_layer.models.UserInfo
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("/auth/login")
    @FormUrlEncoded
    fun loginUser(@Field("username") login: String, @Field("password") password: String): Single<LoginResponse>

    @GET("/users")
    fun getAllUsers(): Single<List<UserInfo>>
}
