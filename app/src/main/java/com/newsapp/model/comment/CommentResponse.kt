package com.newsapp.model.comment

data class CommentResponse(
    val message: String,
    val status: Boolean,
    val status_code: String
)