package com.geeks.hw42.repositories

import com.geeks.hw42.model.core.RetrofitClient
import com.geeks.hw42.model.models.WeatherResponse

class WeatherRepository {
    private val apiKey = "4019aa9d8ddd44c9a47150314250702"

    suspend fun getCurrentWeather(location: String): WeatherResponse =
        RetrofitClient.retrofitClient.getCurrentWeather(apiKey, location)
}