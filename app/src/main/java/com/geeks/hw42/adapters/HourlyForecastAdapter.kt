package com.geeks.hw42.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geeks.hw42.R
import com.geeks.hw42.databinding.ItemTodayBinding
import com.geeks.hw42.model.models.WeatherResponse
import java.text.SimpleDateFormat
import java.util.Locale

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
        val dateTime = hour.time?: ""
        val date = try {
            val parser = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())
            val date = parser.parse(dateTime)
            if (date != null) formatter.format(date) else dateTime
        } catch (e: Exception) {
            dateTime
        }
        holder.binding.date.text = date
        holder.binding.timeDegree.text = "${hour.tempC?.toInt() ?: 0}°"
//        holder.binding.weatherIcon.setImageResource(getIconRes(hour.condition?.code))

        val conditionCode = hour.condition?.code
        holder.binding.weatherIcon.setImageResource(getWeatherIcon(conditionCode))

        val bgColorRes = getBackgroundColor(conditionCode)
        holder.binding.root.setBackgroundColor(ContextCompat.getColor(holder.binding.root.context, bgColorRes))
    }

    override fun getItemCount(): Int = hours.size

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
