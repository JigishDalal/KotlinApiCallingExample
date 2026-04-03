package com.example.kotlinapicalling.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    
    private const val BASE_URL = "https://meme-api.com/"
    
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    val memeApiService: MemeApiService by lazy {
        retrofit.create(MemeApiService::class.java)
    }
}
