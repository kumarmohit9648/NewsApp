package com.knovatik.navadesh.model.comment

data class CommentRequest(
    val auth_token: String,
    val comment: String,
    val post_id: String
)