package com.newsapp.model

data class CommonResponse(
    val data: Any?,
    val message: String,
    val status: Boolean,
    val status_code: String
)