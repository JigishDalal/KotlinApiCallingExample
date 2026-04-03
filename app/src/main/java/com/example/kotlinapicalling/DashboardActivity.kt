package com.example.kotlinapicalling

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapicalling.data.api.RetrofitClient
import com.example.kotlinapicalling.ui.adapter.MemeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity : AppCompatActivity() {
    
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var rvMemes: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private lateinit var btnRetry: Button
    private lateinit var toolbar: Toolbar
    private lateinit var memeAdapter: MemeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        
        // Initialize Views
        toolbar = findViewById(R.id.toolbar)
        rvMemes = findViewById(R.id.rvMemes)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)
        btnRetry = findViewById(R.id.btnRetry)
        
        // Setup Toolbar
        setSupportActionBar(toolbar)
        
        // Setup RecyclerView
        memeAdapter = MemeAdapter()
        rvMemes.layoutManager = LinearLayoutManager(this)
        rvMemes.adapter = memeAdapter
        
        // Retry button click listener
        btnRetry.setOnClickListener {
            fetchMemes()
        }
        
        // Fetch memes from API
        fetchMemes()
    }

    private fun fetchMemes() {
        // Show loading
        progressBar.visibility = View.VISIBLE
        tvError.visibility = View.GONE
        btnRetry.visibility = View.GONE
        rvMemes.visibility = View.GONE
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.memeApiService.getMemes(5)
                
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    
                    if (response.isSuccessful && response.body() != null) {
                        val memeResponse = response.body()!!
                        memeAdapter.updateMemes(memeResponse.memes)
                        rvMemes.visibility = View.VISIBLE
                    } else {
                        showError()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError()
                }
            }
        }
    }
    
    private fun showError() {
        progressBar.visibility = View.GONE
        rvMemes.visibility = View.GONE
        tvError.visibility = View.VISIBLE
        btnRetry.visibility = View.VISIBLE
        Toast.makeText(this, "Error: ${resources.getString(R.string.app_name)}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        // Clear login state from SharedPreferences
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

        // Navigate back to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
