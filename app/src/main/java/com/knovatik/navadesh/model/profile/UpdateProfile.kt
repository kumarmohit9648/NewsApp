package com.knovatik.navadesh.model.profile

import java.io.Serializable

data class UpdateProfile(
    val address: String,
    val auth_token: String,
    val city: String,
    val country: String,
    val email_id: String,
    val gender: String,
    val name: String,
    val pin_code: String,
    val state: String
) : Serializable