package com.mendosal.locationsweather.data.network

/**
 * Created by Luis Mendoza on 8/27/2021.
 */
sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {

    class Success<T>(data: T): Resource<T>(data)

    class Loading<T>(data: T? = null): Resource<T>(data)

    class Error<T>(throwable: Throwable, data: T? = null): Resource<T>(data, throwable)
}