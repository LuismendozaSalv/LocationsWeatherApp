package com.mendosal.locationsweather.app

import android.app.Application

/**
 * Created by Luis Mendoza on 8/26/2021.
 */
class MyApp: Application() {
    companion object {
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}