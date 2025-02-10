package com.geeks.hw42.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geeks.hw42.databinding.ItemTodayBinding
import com.geeks.hw42.model.models.WeatherResponse

class HourlyForecastAdapter(private val hours: List<WeatherResponse.Hour>) :
    RecyclerView.Adapter<HourlyForecastAdapter.HourViewHolder>() {

    inner class HourViewHolder(val binding: ItemTodayBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val binding = ItemTodayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val hour = hours[position]
        holder.binding.time.text = hour.time?.substringAfter(" ")
        holder.binding.timeDegree.text = "${hour.tempC?.toInt() ?: 0}°"
//        holder.binding.weatherIcon.setImageResource(getIconRes(hour.condition?.code))
    }
    override fun getItemCount(): Int = hours.size
}
