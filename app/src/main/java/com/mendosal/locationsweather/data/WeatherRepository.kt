package com.mendosal.locationsweather.data

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.mendosal.locationsweather.app.MyApp
import com.mendosal.locationsweather.data.local.CityWeatherRoomDatabase
import com.mendosal.locationsweather.data.local.dao.CityWeatherDao
import com.mendosal.locationsweather.data.remote.WeatherApiService
import com.mendosal.locationsweather.data.remote.WeatherClient
import com.mendosal.locationsweather.data.remote.models.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Luis Mendoza on 8/26/2021.
 */
class WeatherRepository {
    var weatherApiService: WeatherApiService? = null
    var weatherClient: WeatherClient? = null
    var weatherInfo: MutableLiveData<WeatherResponse>? = null
    var cityWeatherDao: CityWeatherDao? = null

    init {
        weatherClient = WeatherClient.instance
        weatherApiService = weatherClient?.getWeatherService()
        weatherInfo = weatherInformation()
        val db = Room.databaseBuilder(
            MyApp.instance,
            CityWeatherRoomDatabase::class.java, "city_weather_db"
        ).build()
        cityWeatherDao = db.cityWeatherDao()

    }

    fun weatherInformation(): MutableLiveData<WeatherResponse>? {
        if (weatherInfo == null) {
            weatherInfo = MutableLiveData<WeatherResponse>()
        }

        val call: Call<WeatherResponse>? =
                weatherApiService?.getWeatherByCoordinates(-17.4455922,-65.7946595)
        call?.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {

                    weatherInfo?.value = response.body()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error en la llamada", Toast.LENGTH_LONG).show()
            }
        })
        return weatherInfo
    }
}