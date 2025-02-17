package com.geeks.hw42.fragments.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.hw42.R
import com.geeks.hw42.adapters.WeatherAdapter
import com.geeks.hw42.adapters.HourlyForecastAdapter
import com.geeks.hw42.databinding.FragmentMainBinding
import com.geeks.hw42.model.models.WeatherResponse
import com.geeks.hw42.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val repository = WeatherRepository()
    private val adapter = WeatherAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       loadData()
        setupListener()
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                val weatherResponse = withContext(Dispatchers.IO){
                    repository.getCurrentWeather("Tokmok")
                }
                showWeather(weatherResponse)
            } catch (e: Exception) {
                showError(e.message ?: "Unknown error")
            }
        }
    }

    private fun setupListener() {
        binding.notice.setOnClickListener {
            findNavController().navigate(R.id.noticeFragment)
        }
    }

    private fun showWeather(weatherResponse: WeatherResponse) {
        binding.apply {
            weatherResponse.current?.let {
                degree.text = "${it.tempC?.toInt() ?: 0}°C"
                etPrecipitation1.text = "Max: ${it.precipMm ?: 0}"
                etPrecipitation2.text = "Min: ${it.precipIn ?: 0}"
                etAirHumidity.text = "${it.humidity ?: 0}%"
                etAirTemperature.text = "${it.feelslikeC?.toInt() ?: 0}°C"
                etWindSpeed.text = "${it.windKph?.toInt() ?: 0} km/h"
            }

            val list = mutableListOf<String>()
            weatherResponse.location?.name?.let { list.add(it) }
            repeat(5) { list.add("2") }
            adapter.submitList(list)
        }
    }

    private fun initRecyclerView() {
        binding.rvNextForecast.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapter
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

//        weatherResponse.forecast?.forecastday?.let { forecastDays ->
//            forecastDays.firstOrNull()?.hour?.let { hourlyList ->
//                val hourlyAdapter = HourlyForecastAdapter(hourlyList)
//                binding.rvToday.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//                binding.rvToday.adapter = hourlyAdapter
//            }
//            if (forecastDays.size > 1) {
//                val dailyList = forecastDays.subList(1, forecastDays.size)
//                val dailyAdapter = WeatherAdapter(dailyList)
//                binding.rvNextForecast.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//                binding.rvNextForecast.adapter = dailyAdapter
//            }
//        }
//    }

//
//    private fun showError(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//    }
