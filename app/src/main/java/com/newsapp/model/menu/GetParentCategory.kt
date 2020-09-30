package com.newsapp.model.menu

import java.io.Serializable

data class GetParentCategory(
    val block_type: String,
    val category_order: String,
    val color: String,
    val created_at: String,
    val description: String,
    val id: String,
    val keywords: String,
    val lang_id: String,
    val name: String,
    val name_slug: String,
    val parent_id: String,
    val show_at_homepage: String,
    val show_on_menu: String
) : Serializable