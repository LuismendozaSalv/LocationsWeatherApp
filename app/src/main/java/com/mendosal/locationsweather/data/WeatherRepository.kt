package com.mendosal.locationsweather.data

import androidx.room.withTransaction
import com.mendosal.locationsweather.data.local.CityWeatherRoomDatabase
import com.mendosal.locationsweather.data.local.entity.CityWeatherEntity
import com.mendosal.locationsweather.data.network.networkBoundResource
import com.mendosal.locationsweather.data.remote.WeatherApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by Luis Mendoza on 8/26/2021.
 */
class WeatherRepository @Inject constructor(
    private val api: WeatherApiService,
    private val db: CityWeatherRoomDatabase
) {

    private val cityWeatherDao = db.cityWeatherDao()

    fun getWeatherInfo(cityName: String) = networkBoundResource(
        query = {
            cityWeatherDao.getCityWeather(cityName)
        },
        fetch = {
            delay(2000)
            api.getWeatherByCityName(cityName)
        },
        saveFetchResult = { weatherResponse ->
            db.withTransaction {
                val cityWeather = CityWeatherEntity.getCityWeatherFromResponse(weatherResponse)
                cityWeatherDao.insert(cityWeather)
            }
        }
    )


}