package com.example.kotlinapicalling

import android.app.Application
import com.example.kotlinapicalling.data.api.RetrofitClient

/**
 * Application class that initializes app-wide components.
 * Handles the initialization of RetrofitClient with application context.
 */
class MemeApplication : Application() {
    
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects have been created.
     * Initializes RetrofitClient with the application context.
     */
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.init(applicationContext)
    }
}
