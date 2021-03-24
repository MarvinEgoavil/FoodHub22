package com.example.foodhub.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //  private val AUTH = "Basic "+ Base64.encodeToString("belalkhan:123456".toByteArray(), Base64.NO_WRAP)
    //private const val BASE_URL = "https://foodhubservice.herokuapp.com/api/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                //  .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    fun instanceFood(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://foodhubservice.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*val instanceFood: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://foodhubservice.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }*/
    val instanceMenu: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.androidhive.info/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }

}