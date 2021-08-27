package com.mendosal.locationsweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mendosal.locationsweather.data.WeatherRepository
import com.mendosal.locationsweather.data.remote.WeatherClient
import com.mendosal.locationsweather.data.remote.models.WeatherResponse

/**
 * Created by Luis Mendoza on 8/27/2021.
 */
class MapsViewModel: ViewModel() {
    private var weatherRepository: WeatherRepository
    private var weatherInfo: LiveData<WeatherResponse>

    init {
        weatherRepository = WeatherRepository()
        weatherInfo = weatherRepository.weatherInformation()!!
    }

    fun getWeatherInfo(): LiveData<WeatherResponse> {
        return weatherInfo
    }
}