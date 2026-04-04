package com.example.kotlinapicalling.data.api

import android.content.Context
import com.example.kotlinapicalling.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RetrofitClient is a singleton object that provides a centralized Retrofit configuration.
 * It creates and manages the Retrofit instance and the MemeApiService implementation.
 */
object RetrofitClient {

    private lateinit var BASE_URL: String

    fun init(context: Context) {
        BASE_URL = context.getString(R.string.base_url)
    }

    /**
     * Lazy-initialized Retrofit instance configured with the base URL
     * and Gson converter for JSON parsing.
     */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    /**
     * Lazy-initialized MemeApiService instance that provides access to the meme API endpoints.
     */
    val memeApiService: MemeApiService by lazy {
        retrofit.create(MemeApiService::class.java)
    }
}
