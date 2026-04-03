package com.example.kotlinapicalling.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinapicalling.MemeDetailActivity
import com.example.kotlinapicalling.R
import com.example.kotlinapicalling.data.model.Meme

class MemeAdapter(
    private val memes: MutableList<Meme> = mutableListOf()
) : RecyclerView.Adapter<MemeAdapter.MemeViewHolder>() {

    class MemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMeme: ImageView = itemView.findViewById(R.id.ivMeme)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val tvUps: TextView = itemView.findViewById(R.id.tvUps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meme, parent, false)
        return MemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val meme = memes[position]
        
        holder.tvTitle.text = meme.title
        holder.tvAuthor.text = "by ${meme.author}"
        holder.tvUps.text = "👍 ${meme.ups}"
        
        // Load image with Glide
        Glide.with(holder.itemView.context)
            .load(meme.url)
            .placeholder(R.color.purple_200)
            .error(R.color.purple_700)
            .centerCrop()
            .into(holder.ivMeme)
        
        // Set click listener to open MemeDetailActivity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MemeDetailActivity::class.java).apply {
                putExtra(MemeDetailActivity.EXTRA_POST_LINK, meme.postLink)
                putExtra(MemeDetailActivity.EXTRA_SUBREDDIT, meme.subreddit)
                putExtra(MemeDetailActivity.EXTRA_TITLE, meme.title)
                putExtra(MemeDetailActivity.EXTRA_URL, meme.url)
                putExtra(MemeDetailActivity.EXTRA_AUTHOR, meme.author)
                putExtra(MemeDetailActivity.EXTRA_UPS, meme.ups)
                putExtra(MemeDetailActivity.EXTRA_NSFW, meme.nsfw)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = memes.size

    fun updateMemes(newMemes: List<Meme>) {
        memes.clear()
        memes.addAll(newMemes)
        notifyDataSetChanged()
    }
}
