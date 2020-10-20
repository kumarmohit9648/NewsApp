package com.knovatik.navadesh.model.posts

data class Data(
    val comment_count: String,
    val content: String,
    val dislike_count: String,
    val id: String,
    val image_big: String,
    val like_count: String,
    val share_count: String,
    val summary: String,
    val title: String,
    val like_status: String,
    val share_link: String,
    val aging: String,
    val comment_list: List<Comment>
)