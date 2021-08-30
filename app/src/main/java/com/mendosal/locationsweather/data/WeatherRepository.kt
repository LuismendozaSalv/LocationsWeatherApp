package com.mendosal.locationsweather.data

import androidx.lifecycle.asLiveData
import androidx.room.withTransaction
import com.mendosal.locationsweather.data.local.CityWeatherRoomDatabase
import com.mendosal.locationsweather.data.local.Default
import com.mendosal.locationsweather.data.local.entity.CityWeatherEntity
import com.mendosal.locationsweather.data.network.networkBoundResource
import com.mendosal.locationsweather.data.remote.WeatherApiService
import com.mendosal.locationsweather.utils.DataStoreKeys
import com.mendosal.locationsweather.utils.DataStoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Luis Mendoza on 8/26/2021.
 */
class WeatherRepository @Inject constructor(
        private val api: WeatherApiService,
        private val db: CityWeatherRoomDatabase,
        private val dataStoreManager: DataStoreManager
) {
    private val cityWeatherDao = db.cityWeatherDao()

    fun getDefaultCityWeatherList() = networkBoundResource(
            query = {
                cityWeatherDao.getAll()
            },
            fetch = {
                Default.getDefaultCityWeatherList()
            },
            saveFetchResult = {
                db.withTransaction {
                    val value = dataStoreManager.readValue(DataStoreKeys.INIT_LOAD_KEY)
                    if (value != "Y") {
                        cityWeatherDao.insertCityWeatherList(Default.getDefaultCityWeatherList())
                    }
                }
            }
    )

    fun getWeatherInfo(cityName: String) = networkBoundResource(
            query = {
                cityWeatherDao.getCityWeather(cityName)
            },
            fetch = {
                delay(1000)
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