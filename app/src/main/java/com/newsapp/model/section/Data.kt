package com.newsapp.model.section

data class Data(
    val id: String,
    val content: String,
    val content_url: String,
    val audio_url: String,
    val color: String,
    val link: String,
    val type: Int,
    val video_url: String,
    val section_id: String,
    var isPlay: Int = 0
)