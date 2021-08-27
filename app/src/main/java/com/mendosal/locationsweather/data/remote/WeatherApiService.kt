package com.mendosal.locationsweather.data.remote

import com.mendosal.locationsweather.data.remote.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Luis Mendoza on 8/26/2021.
 */
interface WeatherApiService {

    @GET("weather")
    fun getWeatherByCoordinates(@Query("lat") lat: Double,
                                @Query("lon") lon: Double): Call<WeatherResponse>
}