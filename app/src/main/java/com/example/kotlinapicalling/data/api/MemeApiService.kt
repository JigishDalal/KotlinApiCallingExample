package com.example.kotlinapicalling.data.api

import com.example.kotlinapicalling.data.model.MemeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MemeApiService {
    
    @GET("gimme/{count}")
    suspend fun getMemes(@Path("count") count: Int): Response<MemeResponse>
}
