package com.mendosal.locationsweather.data.local.dao

import androidx.room.*
import com.mendosal.locationsweather.data.local.entity.CityWeatherEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Luis Mendoza on 8/27/2021.
 */
@Dao
interface CityWeatherDao {
    @Query("SELECT * FROM cityweather")
    fun getAll(): Flow<List<CityWeatherEntity>>

    @Query("SELECT * FROM cityweather WHERE city_name like :cityName")
    fun getCityWeather(cityName: String): Flow<List<CityWeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cityWeather: CityWeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cityWeatherList: List<CityWeatherEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityWeatherList(cityWeatherList: List<CityWeatherEntity>)

}