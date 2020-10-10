package com.newsapp.model.menu

import java.io.Serializable

data class Data(
    val id: String,
    val name: String,
    val color: String,
    val is_menu: String,
    val icon: String,
) : Serializable