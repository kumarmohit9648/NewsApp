package com.newsapp.model.notification

data class Notification(
    val data: List<NotificationList>?,
    val message: String,
    val status: Boolean,
    val status_code: String
)