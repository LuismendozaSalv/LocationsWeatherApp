package com.mendosal.locationsweather.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mendosal.locationsweather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Luis Mendoza on 8/27/2021.
 */
@HiltViewModel
class MapsViewModel @Inject constructor(
    repository: WeatherRepository
) : ViewModel() {

    var weatherInfo = repository
        .getWeatherInfo("Santa Cruz de la Sierra").asLiveData()
}