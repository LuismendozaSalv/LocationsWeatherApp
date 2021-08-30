package com.mendosal.locationsweather.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mendosal.locationsweather.data.local.CityWeatherRoomDatabase
import com.mendosal.locationsweather.data.remote.ApiConstants
import com.mendosal.locationsweather.data.remote.RequestInterceptor
import com.mendosal.locationsweather.data.remote.WeatherApiService
import com.mendosal.locationsweather.utils.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Luis Mendoza on 8/27/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(RequestInterceptor())

        val client = okHttpClientBuilder.build()

        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideRestaurantApi(retrofit: Retrofit): WeatherApiService =
        retrofit.create(WeatherApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CityWeatherRoomDatabase =
        Room.databaseBuilder(
            app,
            CityWeatherRoomDatabase::class.java, "city_weather_db"
        ).build()

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}