package com.knovatik.navadesh.model.weather

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Long,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)