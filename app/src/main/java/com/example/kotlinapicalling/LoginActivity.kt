package com.example.kotlinapicalling

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

/**
 * LoginActivity handles user authentication for the application.
 * It uses SharedPreferences to persist login state and validates
 * credentials against fixed admin credentials.
 */
class LoginActivity : AppCompatActivity() {
    
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button

    /**
     * Called when the activity is starting.
     * Initializes views, SharedPreferences, and checks if user is already logged in.
     * Sets up login button click listener to validate credentials.
     * 
     * @param savedInstanceState If the activity is being re-initialized after previously
     * being shut down then this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        
        // Initialize SharedPreferences
        //MODE_PRIVATE: This is the default and recommended mode. It means the file can be accessed by only the calling application.

        //MODE_APPEND:  Used to append new preferences to existing ones rather than overwriting the entire file.
        //val fos = openFileOutput("data.txt", MODE_APPEND)
        //fos.write("New Data\n".toByteArray())

        //MODE_ENABLE_WRITE_AHEAD_LOGGING:faster database writes in SQLite (improves performance)
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        
        // Initialize Views
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        
        // Check if user is already logged in
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            goToDashboard()
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Fixed Credentials: admin / Admin@123
            if (email == "admin" && password == "Admin@123") {
                // Save login state
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.putString("userEmail", email)
                editor.apply()

                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                goToDashboard()
            } else {
                Toast.makeText(this, "Invalid Credentials. Use admin / Admin@123", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Navigates to the DashboardActivity and finishes the current login screen.
     * This prevents the user from navigating back to the login screen using back button.
     */
    private fun goToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish() // Close login screen so user can't go back
    }
}
