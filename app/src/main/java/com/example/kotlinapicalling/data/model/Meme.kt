package com.example.kotlinapicalling.data.model

import com.google.gson.annotations.SerializedName

data class MemeResponse(
    val count: Int,
    val memes: List<Meme>
)

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
