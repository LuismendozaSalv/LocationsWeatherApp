package com.mendosal.locationsweather.data.local

import com.mendosal.locationsweather.data.local.entity.CityWeatherEntity

/**
 * Created by Luis Mendoza on 8/28/2021.
 */
class Default {

    companion object {
        fun getDefaultCityWeatherList(): List<CityWeatherEntity> {
            return listOf(
                    CityWeatherEntity(3904906, "Santa Cruz de la Sierra", "-63.1667", "\t-17.8",
                            803, "muy nuboso", "16.03", "10", "88", "75",
                            1630152314, -14400),
                    CityWeatherEntity(3441575, "Montevideo", "-56.1674", "-34.8335",
                            800, "cielo claro", "9.42", "10", "84", "0",
                            1630151813, -10800),
                    CityWeatherEntity(3439389, "Asunción", "-57.6359", "-25.3007",
                            800, "cielo claro", "15.69", "10", "94", "0",
                            1630151812, -14400),
                    CityWeatherEntity(3435910, "Buenos Aires", "-58.3772", "-34.6132",
                            803, "muy nubos", "13.42", "10", "68", "74",
                            1630152099, -10800),
                    CityWeatherEntity(3469058, "Brasília", "-47.9297", "-15.7797",
                            800, "cielo claro", "21.51", "10", "46", "0",
                            1630152180, -10800)
            )
        }
    }
}