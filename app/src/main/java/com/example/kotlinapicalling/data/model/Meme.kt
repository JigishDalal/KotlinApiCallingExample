package com.example.kotlinapicalling.data.model

import com.google.gson.annotations.SerializedName

/**
 * MemeResponse represents the API response containing a list of memes.
 * @property count The number of memes in the response.
 * @property memes The list of Meme objects.
 */
data class MemeResponse(
    val count: Int,
    val memes: List<Meme>
)

/**
 * Meme represents a single meme object from the API.
 * @property postLink The URL link to the original Reddit post.
 * @property subreddit The name of the subreddit where the meme was posted.
 * @property title The title of the meme.
 * @property url The direct URL to the meme image.
 * @property nsfw Whether the meme is marked as Not Safe For Work.
 * @property spoiler Whether the meme contains spoilers.
 * @property author The username of the meme's author.
 * @property ups The number of upvotes the meme has received.
 * @property preview List of preview image URLs.
 */
data class Meme(
    val postLink: String,
    val subreddit: String,
    val title: String,
    val url: String,
    val nsfw: Boolean,
    val spoiler: Boolean,
    val author: String,
    val ups: Int,
    val preview: List<String>
)
