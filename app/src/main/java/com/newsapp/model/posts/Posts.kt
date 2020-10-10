package com.newsapp.model.posts

data class Posts(
    val data: List<Data>?,
    val message: String,
    val status: Boolean,
    val status_code: String
)