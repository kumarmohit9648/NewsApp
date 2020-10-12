package com.newsapp.model.posts

data class Comment(
    val comment: String,
    val user_name: String,
    val id: String,
    val profile_image: String
)