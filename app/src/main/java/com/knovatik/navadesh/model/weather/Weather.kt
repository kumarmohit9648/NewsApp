package com.knovatik.navadesh.model.weather

data class Weather(
    val current: Current,
    val error: Error?,
    val location: Location
)