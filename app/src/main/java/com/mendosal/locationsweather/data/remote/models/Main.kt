package com.mendosal.locationsweather.data.remote.models

data class Main(
    val feels_like: Float,
    val humidity: Int,
    val pressure: Int,
    val temp: Float,
    val temp_max: Float,
    val temp_min: Float
)