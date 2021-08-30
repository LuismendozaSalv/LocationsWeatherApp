package com.mendosal.locationsweather.ui.weatherInfo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mendosal.locationsweather.data.WeatherRepository
import com.mendosal.locationsweather.data.local.entity.CityWeatherEntity
import com.mendosal.locationsweather.data.network.Resource
import com.mendosal.locationsweather.data.remote.models.ExtraInfo
import com.mendosal.locationsweather.utils.DateUtils.Companion.formatTo
import com.mendosal.locationsweather.utils.ImageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.*
import java.util.*
import javax.inject.Inject

/**
 * Created by Luis Mendoza on 8/27/2021.
 */
@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    val repository: WeatherRepository
) : ViewModel() {
    var cityName: String = ""

    fun getCityWeatherInfo(): LiveData<Resource<List<CityWeatherEntity>>> {
        return repository.getWeatherInfo(cityName).asLiveData()
    }


    fun getExtraInfo(cityWeatherEntity: CityWeatherEntity): ExtraInfo {
        val epoch = cityWeatherEntity.dt!!.toLong() * 1000L
        val updatedate = Date(epoch);
        val localDate = updatedate.formatTo("dd/MM/yyyy")
        val localTime = updatedate.formatTo("hh:mm")
        val weatherIcon = cityWeatherEntity.weatherId?.let { ImageUtils.getWeatherIcon(it) }
        return ExtraInfo(localDate,localTime,weatherIcon!!)
    }

}