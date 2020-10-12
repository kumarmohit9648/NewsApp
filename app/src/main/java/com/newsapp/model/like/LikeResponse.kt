package com.newsapp.model.like

data class LikeResponse(
    val data: Any?,
    val message: String,
    val status: Boolean,
    val status_code: String
)