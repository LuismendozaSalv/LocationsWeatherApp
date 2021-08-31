package com.mendosal.locationsweather.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mendosal.locationsweather.data.WeatherRepository
import com.mendosal.locationsweather.data.local.entity.CityWeatherEntity
import com.mendosal.locationsweather.data.network.Resource
import com.mendosal.locationsweather.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Luis Mendoza on 8/27/2021.
 */
@HiltViewModel
class MapsViewModel @Inject constructor(
    val repository: WeatherRepository,
    val dataStoreManager: DataStoreManager
) : ViewModel() {
    var cityName: String = ""

    var defaultCities = repository.getDefaultCityWeatherList().asLiveData()

    var weatherInfo = repository
        .getWeatherInfo(cityName).asLiveData()

    fun getCityWeatherInfo(): LiveData<Resource<List<CityWeatherEntity>>> {
        return repository
                .getWeatherInfo(cityName).asLiveData()
    }

    fun getCityWeatherInfo(lat: String, lon: String): LiveData<Resource<List<CityWeatherEntity>>> {
        return repository
                .getWeatherInfo(lat, lon).asLiveData()
    }

    fun readValue(key: String){
        viewModelScope.launch {
            dataStoreManager.readValue(key)
        }
    }

    fun storeValue(key: String, value: String) {
        viewModelScope.launch {
            dataStoreManager.storeValue(key, value)
        }
    }
}