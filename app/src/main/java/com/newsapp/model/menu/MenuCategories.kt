package com.newsapp.model.menu

import java.io.Serializable

data class MenuCategories(
    val get_parent_categories: List<GetParentCategory>?,
    val message: String,
    val status: Boolean,
    val status_code: String
) : Serializable