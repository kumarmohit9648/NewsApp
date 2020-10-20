package com.knovatik.navadesh.model.menu

import java.io.Serializable

data class MenuCategories(
    val data: List<Data>?,
    val message: String,
    val status: Boolean,
    val status_code: String
) : Serializable