package com.newsapp.model.register

data class RegisterResponse(
    val data: Data?,
    val message: String,
    val status: Boolean,
    val status_code: String
)