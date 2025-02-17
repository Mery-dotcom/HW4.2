package com.geeks.hw42.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geeks.hw42.databinding.RvNextForecastBinding

class WeatherAdapter : ListAdapter<String, WeatherAdapter.WeatherViewHolder>(WeatherDiffCallback()) {

    class WeatherViewHolder(private val binding: RvNextForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherDay: String) {
            binding.txtWeek.text = weatherDay
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            RvNextForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}



