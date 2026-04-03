package com.example.kotlinapicalling

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide

class MemeDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_POST_LINK = "extra_post_link"
        const val EXTRA_SUBREDDIT = "extra_subreddit"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_URL = "extra_url"
        const val EXTRA_AUTHOR = "extra_author"
        const val EXTRA_UPS = "extra_ups"
        const val EXTRA_NSFW = "extra_nsfw"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme_detail)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Meme Details"

        // Get data from intent
        val postLink = intent.getStringExtra(EXTRA_POST_LINK) ?: ""
        val subreddit = intent.getStringExtra(EXTRA_SUBREDDIT) ?: ""
        val title = intent.getStringExtra(EXTRA_TITLE) ?: ""
        val url = intent.getStringExtra(EXTRA_URL) ?: ""
        val author = intent.getStringExtra(EXTRA_AUTHOR) ?: ""
        val ups = intent.getIntExtra(EXTRA_UPS, 0)
        val nsfw = intent.getBooleanExtra(EXTRA_NSFW, false)

        // Initialize views
        val ivMeme = findViewById<ImageView>(R.id.ivMemeDetail)
        val tvTitle = findViewById<TextView>(R.id.tvTitleDetail)
        val tvAuthor = findViewById<TextView>(R.id.tvAuthorDetail)
        val tvSubreddit = findViewById<TextView>(R.id.tvSubreddit)
        val tvUps = findViewById<TextView>(R.id.tvUpsDetail)
        val tvNsfw = findViewById<TextView>(R.id.tvNsfw)

        // Set data to views
        tvTitle.text = title
        tvAuthor.text = "by $author"
        tvSubreddit.text = "r/$subreddit"
        tvUps.text = "👍 $ups upvotes"
        tvNsfw.text = if (nsfw) "NSFW: Yes" else "NSFW: No"
        tvNsfw.setTextColor(if (nsfw) resources.getColor(R.color.purple_700, null) else resources.getColor(R.color.teal_700, null))

        // Load image with Glide
        Glide.with(this)
            .load(url)
            .placeholder(R.color.purple_200)
            .error(R.color.purple_700)
            .into(ivMeme)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
