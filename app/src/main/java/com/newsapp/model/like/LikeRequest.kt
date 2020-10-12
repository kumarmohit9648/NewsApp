package com.newsapp.model.like

data class LikeRequest(
    val auth_token: String,
    val post_id: String,
    val status: String
)