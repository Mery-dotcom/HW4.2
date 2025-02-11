package com.geeks.hw42.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geeks.hw42.R
import com.geeks.hw42.databinding.RvNextForecastBinding
import com.geeks.hw42.model.models.WeatherResponse

class DailyForecastAdapter(private val days: List<WeatherResponse.ForecastDay>) :
    RecyclerView.Adapter<DailyForecastAdapter.DayViewHolder>() {

    inner class DayViewHolder(val binding: RvNextForecastBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding =
            RvNextForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val dayForecast = days[position]
        holder.binding.txtWeek.text = dayForecast.date ?: ""

        val maxTemp = dayForecast.day?.maxtempC?.toInt() ?: 0
        holder.binding.txtNextDegreeDay.text = "$maxTemp°"

        val minTemp = dayForecast.day?.mintempC?.toInt() ?: 0
        holder.binding.txtNextDegreeNight.text = "$minTemp°"

        val conditionCode = dayForecast.day?.condition?.code
        holder.binding.ivWeek.setImageResource(getWeatherIcon(conditionCode))

        val colorRes = getBackgroundColor(conditionCode)
        holder.binding.root.setBackgroundColor(
            ContextCompat.getColor(holder.binding.root.context, colorRes)
        )
    }

    override fun getItemCount(): Int = days.size

    private fun getWeatherIcon(code: Int?): Int {
        return when (code) {
            1000 -> R.drawable.clear
            in 1003..1030 -> R.drawable.cloudy
            else -> R.drawable.rainy
        }
    }

    private fun getBackgroundColor(code: Int?): Int {
        return when (code) {
            1000 -> R.color.blue
            in 1003..1030 -> R.color.dark_blue
            else -> R.color.grey
        }
    }
}


