package com.example.swipe.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "https://app.getswipe.in/api/public/"

    fun getInstance(): Retrofit {
        var interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        var client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}