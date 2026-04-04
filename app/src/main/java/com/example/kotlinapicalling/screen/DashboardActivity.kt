package com.example.kotlinapicalling.screen

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.kotlinapicalling.R
import com.example.kotlinapicalling.data.api.RetrofitClient
import com.example.kotlinapicalling.ui.adapter.MemeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * DashboardActivity is the main screen after login that displays a list of memes
 * fetched from the Meme API. It uses RecyclerView with MemeAdapter to show memes
 * and provides logout functionality through the toolbar menu.
 * Supports pull-to-refresh for reloading memes.
 */
class DashboardActivity : AppCompatActivity() {
    
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var rvMemes: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private lateinit var btnRetry: Button
    private lateinit var toolbar: Toolbar
    private lateinit var memeAdapter: MemeAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    /**
     * Called when the activity is starting.
     * Initializes views, sets up RecyclerView with adapter, configures toolbar,
     * sets up SwipeRefreshLayout, and triggers the meme API call.
     * 
     * @param savedInstanceState If the activity is being re-initialized after previously
     * being shut down then this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(getString(R.string.pref_user_prefs), MODE_PRIVATE)
        
        // Initialize Views
        toolbar = findViewById(R.id.toolbar)
        rvMemes = findViewById(R.id.rvMemes)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)
        btnRetry = findViewById(R.id.btnRetry)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        
        // Setup Toolbar
        setSupportActionBar(toolbar)
        
        // Setup RecyclerView
        memeAdapter = MemeAdapter()
        rvMemes.layoutManager = LinearLayoutManager(this)
        rvMemes.adapter = memeAdapter
        
        // Setup SwipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            fetchMemes(isRefresh = true)
        }
        swipeRefresh.setColorSchemeResources(
            R.color.purple_700,
            R.color.teal_700
        )
        
        // Retry button click listener
        btnRetry.setOnClickListener {
            fetchMemes()
        }
        
        // Fetch memes from API
        fetchMemes()
    }

    /**
     * Fetches memes from the Meme API using Retrofit.
     * Shows loading indicator while fetching and updates RecyclerView on success.
     * Displays error message with retry option on failure.
     * Uses coroutines to perform network operation on IO thread.
     * 
     * @param isRefresh If true, indicates this is a pull-to-refresh action.
     *                  Hides the progress bar and shows SwipeRefreshLayout indicator instead.
     */
    private fun fetchMemes(isRefresh: Boolean = false) {
        // Show appropriate loading indicator
        if (isRefresh) {
            // Don't show progress bar for refresh, SwipeRefreshLayout handles it
            tvError.visibility = View.GONE
            btnRetry.visibility = View.GONE
        } else {
            progressBar.visibility = View.VISIBLE
            tvError.visibility = View.GONE
            btnRetry.visibility = View.GONE
            rvMemes.visibility = View.GONE
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.memeApiService.getMemes(5)
                
                withContext(Dispatchers.Main) {
                    // Hide loading indicators
                    progressBar.visibility = View.GONE
                    swipeRefresh.isRefreshing = false
                    
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
                    progressBar.visibility = View.GONE
                    swipeRefresh.isRefreshing = false
                    showError()
                }
            }
        }
    }
    
    /**
     * Displays the error state by showing error message and retry button.
     * Hides the progress bar and RecyclerView.
     */
    private fun showError() {
        progressBar.visibility = View.GONE
        rvMemes.visibility = View.GONE
        tvError.visibility = View.VISIBLE
        btnRetry.visibility = View.VISIBLE
        Toast.makeText(this, getString(R.string.dashboard_error_toast, getString(R.string.app_name)), Toast.LENGTH_SHORT).show()
    }

    /**
     * Inflate the logout menu into the toolbar.
     * 
     * @param menu The options menu in which you place your items.
     * @return True for the menu to be displayed.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    /**
     * Handles selection of menu items in the toolbar.
     * Triggers logout when the logout menu item is selected.
     * 
     * @param item The menu item that was selected.
     * @return True if the event was handled, false otherwise.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Clears the user's login session by removing SharedPreferences data.
     * Navigates back to LoginActivity and clears the back stack to prevent
     * returning to the dashboard without logging in again.
     */
    private fun logout() {
        // Clear login state from SharedPreferences
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        Toast.makeText(this, R.string.dashboard_logout_success, Toast.LENGTH_SHORT).show()

        // Navigate back to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
