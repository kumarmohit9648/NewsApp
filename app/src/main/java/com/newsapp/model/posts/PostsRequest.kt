package com.newsapp.model.posts

data class PostsRequest(
    val category_id: String,
    val sub_category_id: String,
    val auth_token: String
)