package com.mendosal.locationsweather.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Luis Mendoza on 8/27/2021.
 */
class WeatherClient {
    private val weatherApiService: WeatherApiService
    private val retrofit: Retrofit

    companion object {
        var instance: WeatherClient? = null
            get() {
                if (field == null) {
                    instance = WeatherClient()
                }
                return field
            }
    }

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(RequestInterceptor())

        val client = okHttpClientBuilder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        //Instantiating retrofit service from retrofit object
        weatherApiService = retrofit.create(WeatherApiService::class.java)
    }

    fun getWeatherService() = weatherApiService
}