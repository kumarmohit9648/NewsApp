package com.newsapp.model.register

data class RegisterRequest(
    val email: String,
    val mobile_no: String,
    val name: String,
    val password: String
)