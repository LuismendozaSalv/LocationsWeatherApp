package com.mendosal.locationsweather.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Luis Mendoza on 8/27/2021.
 */

@Entity(tableName = "cityweather")
class CityWeatherEntity(
        @PrimaryKey val uid: Int,
        @ColumnInfo(name = "city_name") val cityName: String?,
        @ColumnInfo(name = "weather_info") val weatherInfo: String?,
)