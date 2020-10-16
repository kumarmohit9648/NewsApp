package com.newsapp.model.section

data class SectionItem(
    val data: List<Data>?,
    val message: String,
    val status: Boolean,
    val status_code: String
)