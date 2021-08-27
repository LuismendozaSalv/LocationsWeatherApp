package com.mendosal.locationsweather.data.remote

/**
 * Created by Luis Mendoza on 8/26/2021.
 */
class ApiConstants {
    companion object {
        val BASE_URL: String = "http://api.openweathermap.org/data/2.5/"
        val API_KEY: String = "3d1d857468e5fd91fc98af3e3e504823"

        val URL_PARAM_API_KEY = "appid"
        val URL_PARAM_LANGUAGE = "lang"
    }
}