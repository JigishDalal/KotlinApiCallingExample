package com.example.kotlinapicalling.data.api

import com.example.kotlinapicalling.data.model.CreateMemeRequest
import com.example.kotlinapicalling.data.model.CreateMemeResponse
import com.example.kotlinapicalling.data.model.MemeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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


    // Get meme from specific subreddit
   // gimme/{subreddit} → meme from specific subreddit
    @GET("gimme/{subreddit}")
    suspend fun getMemeBySubreddit(
        @Path("subreddit") subreddit: String
    ): Response<MemeResponse>



    // Get multiple memes from specific subreddit
    //gimme/{subreddit}/{count} → multiple memes from subreddit
    @GET("gimme/{subreddit}/{count}")
    suspend fun getMemesBySubredditAndCount(
        @Path("subreddit") subreddit: String,
        @Path("count") count: Int
    ): Response<MemeResponse>

    //https://meme-api.com/gimme/programming/5?nsfw=true
    /**
     * Example:
     * https://meme-api.com/gimme/programming/5?nsfw=true
     */
    @GET("gimme/{subreddit}/{count}")
    suspend fun getMemesWithParams(
        @Path("subreddit") subreddit: String,
        @Path("count") count: Int,
        @Query("nsfw") includeNsfw: Boolean
    ): Response<MemeResponse>

    // post example
    @POST("createMeme")
    suspend fun createMeme(
        @Body request: CreateMemeRequest
    ): Response<CreateMemeResponse>
/*
    val request = CreateMemeRequest(
        title = "Funny Meme",
        url = "https://image-url.com/meme.jpg",
        author = "John"
    )
    {
        "title": "Funny Meme",
        "url": "https://image-url.com/meme.jpg",
        "author": "John"
    }

    Real POST APIs → used in:

Login
Signup
Upload data
Forms submission

    */

}
