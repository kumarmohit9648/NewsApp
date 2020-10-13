package com.newsapp.model.notification

data class NotificationList(
    val id: String,
    var is_read: String,
    val post_id: String,
    val title: String
)