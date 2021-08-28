package com.mendosal.locationsweather.data.remote

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Luis Mendoza on 8/26/2021.
 */
class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalHttpUrl: HttpUrl = chain.request().url

        val urlWithParams = originalHttpUrl
            .newBuilder()
            .addQueryParameter(ApiConstants.URL_PARAM_API_KEY, ApiConstants.API_KEY)
            .addQueryParameter(ApiConstants.URL_PARAM_LANGUAGE, "es")
            .addQueryParameter(ApiConstants.URL_PARAM_UNITS, "metric")
            .build()
        var request = chain.request()
        request = request.newBuilder().url(urlWithParams)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(request)
    }
}