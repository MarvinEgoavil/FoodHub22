package com.example.foodhub.api

import com.example.foodhub.models.LoginResponse
import com.example.foodhub.models.Product
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("register")
    fun createUser(
        @Field("name") name: String,
        @Field("password") pasword: String,
        @Field("password_confirmation") repasword: String,
        @Field("email") email: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>


    @GET("/json/menu.json")
    fun listMenu(): Call<List<Product>>
}