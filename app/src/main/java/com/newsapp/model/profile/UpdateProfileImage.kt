package com.newsapp.model.profile

data class UpdateProfileImage(
    val auth_token: String,
    val profile_image: String
)