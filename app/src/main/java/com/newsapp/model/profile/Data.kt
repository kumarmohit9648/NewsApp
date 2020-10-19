package com.newsapp.model.profile

import java.io.Serializable

data class Data(
    val active_status: String,
    val address: String,
    val city: String,
    val country: String,
    val email_id: String,
    val full_name: String,
    val gender: String,
    val login_by: Any,
    val main_id: String,
    val mobile_no: String,
    val otp_no: Any,
    val otp_verified: String,
    val password: String,
    val pin_code: String,
    val profile_image: String?,
    val state: String,
    val token: String
) : Serializable