package com.geeks.hw42.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.hw42.R
import com.geeks.hw42.adapters.DailyForecastAdapter
import com.geeks.hw42.adapters.HourlyForecastAdapter
import com.geeks.hw42.databinding.FragmentMainBinding
import com.geeks.hw42.model.models.WeatherResponse
import com.geeks.hw42.presenter.WeatherContract
import com.geeks.hw42.presenter.WeatherPresenter

class MainFragment : Fragment(), WeatherContract.View {

    private lateinit var binding: FragmentMainBinding
    private val presenter by lazy { WeatherPresenter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadData("Tokmok")
    }

    override fun showWeather(weatherResponse: WeatherResponse) {
        weatherResponse.current?.let { current ->
            binding.degree.text = "${current.tempC?.toInt() ?: 0}°C"
            binding.etPrecipitation1.text = "Max: ${current.precipMm ?: 0}"
            binding.etPrecipitation2.text = "Min: ${current.precipIn ?: 0}"
            binding.etAirHumidity.text = "${current.humidity ?: 0}%"
            binding.etAirTemperature.text = "${current.feelslikeC?.toInt() ?: 0}°C"
            binding.etWindSpeed.text = "${current.windKph?.toInt() ?: 0} km/h"

            when (current.condition?.code){
                1000 -> {
                binding.main.setBackgroundColor(resources.getColor(R.color.blue))
                binding.weather.setImageResource(R.drawable.clear)
                }

                in 1003..1030 -> {
                    binding.main.setBackgroundColor(resources.getColor(R.color.dark_blue))
                    binding.weather.setImageResource(R.drawable.rainy)
                } else -> {
                    binding.main.setBackgroundColor(Color.GRAY)
                binding.weather.setImageResource(R.drawable.cloudy1)
                }
            }
        }

        weatherResponse.forecast?.forecastday?.let { forecastDays ->
            forecastDays.firstOrNull()?.hour?.let { hourlyList ->
                val hourlyAdapter = HourlyForecastAdapter(hourlyList)
                binding.rvToday.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rvToday.adapter = hourlyAdapter
            }
            if (forecastDays.size > 1) {
                val dailyList = forecastDays.subList(1, forecastDays.size)
                val dailyAdapter = DailyForecastAdapter(dailyList)
                binding.rvNextForecast.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rvNextForecast.adapter = dailyAdapter
            }
        }
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}