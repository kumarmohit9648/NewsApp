package com.knovatik.navadesh.model.profile

import java.io.Serializable

data class UpdateProfile(
    val auth_token: String,
    val username: String,
    val address: String
) : Serializable