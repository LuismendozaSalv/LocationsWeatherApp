package com.mendosal.locationsweather.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mendosal.locationsweather.data.remote.models.WeatherResponse

/**
 * Created by Luis Mendoza on 8/27/2021.
 */

@Entity(tableName = "cityweather")
class CityWeatherEntity(
        @PrimaryKey(autoGenerate = false) val uid: Int,
        @ColumnInfo(name = "city_name") val cityName: String?,
        @ColumnInfo(name = "coord_lon") val coordLon: String?,
        @ColumnInfo(name = "coord_lat") val coordLat: String?,
        @ColumnInfo(name = "weather_id") val weatherId: Int?,
        @ColumnInfo(name = "weather_description") val weatherDescription: String?,
        @ColumnInfo(name = "temp") val temperature: String?,
        @ColumnInfo(name = "humidity") val humidity: String?,
        @ColumnInfo(name = "clouds") val clouds: String?,
        @ColumnInfo(name = "dt") val dt: Int?,
        @ColumnInfo(name = "timezone") val timezone: Int?,
) {
    companion object {
        fun getCityWeatherFromResponse(weatherResponse: WeatherResponse): CityWeatherEntity {
            return CityWeatherEntity(
                    weatherResponse.id,
                    weatherResponse.name,
                    weatherResponse.coord.lon.toString(),
                    weatherResponse.coord.lat.toString(),
                    weatherResponse.weather[0].id,
                    weatherResponse.weather[0].description,
                    weatherResponse.main.temp.toString(),
                    weatherResponse.main.humidity.toString(),
                    weatherResponse.clouds.all.toString(),
                    weatherResponse.dt,
                    weatherResponse.timezone
            )
        }
    }

}