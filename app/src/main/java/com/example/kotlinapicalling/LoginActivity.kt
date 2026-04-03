package com.example.kotlinapicalling

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        
        // Initialize SharedPreferences
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

    private fun goToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish() // Close login screen so user can't go back
    }
}
