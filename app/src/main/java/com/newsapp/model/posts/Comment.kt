package com.newsapp.model.posts

data class Comment(
    val comment: String,
    val user_name: String,
    val created_at: String,
    val id: String,
    val playlist_id: String,
    val user_id: String,
    val video_id: String
)