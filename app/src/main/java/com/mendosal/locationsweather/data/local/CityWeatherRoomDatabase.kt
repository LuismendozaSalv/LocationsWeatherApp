package com.mendosal.locationsweather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mendosal.locationsweather.data.local.dao.CityWeatherDao
import com.mendosal.locationsweather.data.local.entity.CityWeatherEntity

/**
 * Created by Luis Mendoza on 8/27/2021.
 */
@Database(entities = [CityWeatherEntity::class], version = 1)
abstract class CityWeatherRoomDatabase: RoomDatabase() {
    abstract fun cityWeatherDao(): CityWeatherDao
}