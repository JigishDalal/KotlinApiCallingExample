package com.example.kotlinapicalling.data.api

import com.example.kotlinapicalling.data.model.MemeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * MemeApiService is a Retrofit interface that defines the API endpoints
 * for fetching memes from the Meme API (https://meme-api.com).
 */
interface MemeApiService {
    
    /**
     * Fetches a specified number of memes from the API.
     * 
     * @param count The number of memes to fetch.
     * @return A Response containing MemeResponse with the list of memes.
     */
    @GET("gimme/{count}")
    suspend fun getMemes(@Path("count") count: Int): Response<MemeResponse>
}
