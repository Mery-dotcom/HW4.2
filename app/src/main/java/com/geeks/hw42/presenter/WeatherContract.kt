package com.geeks.hw42.presenter

import com.geeks.hw42.model.models.WeatherResponse

interface WeatherContract {
    interface View {
        fun showWeather(weatherResponse: WeatherResponse)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadData(location: String)
        fun onDestroy()
    }
}