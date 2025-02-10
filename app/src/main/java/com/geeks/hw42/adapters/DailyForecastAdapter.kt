package com.geeks.hw42.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geeks.hw42.databinding.RvNextForecastBinding
import com.geeks.hw42.model.models.WeatherResponse

class DailyForecastAdapter(private val days: List<WeatherResponse.ForecastDay>) :
    RecyclerView.Adapter<DailyForecastAdapter.DayViewHolder>() {

    inner class DayViewHolder(val binding: RvNextForecastBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = RvNextForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val dayForecast = days[position]
        holder.binding.txtWeek.text = dayForecast.date
        holder.binding.txtNextDegreeDay.text =
            "${dayForecast.day?.mintempC?.toInt() ?: 0}° - ${dayForecast.day?.maxtempC?.toInt() ?: 0}°"
        holder.binding.txtNextDegreeNight.text =
            "${dayForecast.day?.mintempC?.toInt() ?: 0}° - ${dayForecast.day?.maxtempC?.toInt() ?: 0}°"
    }

    override fun getItemCount(): Int = days.size
}
