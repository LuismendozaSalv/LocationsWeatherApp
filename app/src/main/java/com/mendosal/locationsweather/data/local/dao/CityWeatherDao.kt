package com.mendosal.locationsweather.data.local.dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mendosal.locationsweather.data.local.entity.CityWeatherEntity

/**
 * Created by Luis Mendoza on 8/27/2021.
 */
interface CityWeatherDao {
    @Query("SELECT * FROM cityweather")
    fun getAll(): List<CityWeatherEntity>

    @Insert
    fun insertAll(vararg citiesWeather: CityWeatherEntity)

    @Update
    fun update(cityWeather: CityWeatherEntity)

}