package com.knovatik.navadesh.model.posts

data class PostStatus(
    val message: String,
    val post_detail: PostDetailX?,
    val request_status: String,
    val status: Boolean,
    val status_code: String
)