package com.weathertest

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.weather.model.ForecastWeather
import com.weathertest.utils.AppUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_weather.view.*
import java.text.SimpleDateFormat

class ForecastWeatherAdapter(
    private val context: Context,
    private val mDatas: List<ForecastWeather>
) : RecyclerView.Adapter<ForecastWeatherAdapter.ViewHolder>() {

    val apiFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val displayFormat = SimpleDateFormat("dd-MM HH:mm")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData = mDatas[position]

        holder.weatherTxt.text = weatherData.weather[0].main
        holder.windTxt.text = "Wind: " + weatherData.wind.speed.toString()

        if ((context as MainActivity).rdo_celsius.isChecked)
            holder.tempTxt.text = "Temp.: " + weatherData.main.temp.toString() + "°C"
        else holder.tempTxt.text = "Temp.: " + weatherData.main.temp.toString() + "°F"

        holder.dateTxt.text = apiFormat.parse(weatherData.dtTxt)?.let { displayFormat.format(it) }
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weatherTxt = itemView.txt_weather
        val dateTxt = itemView.txt_date
        val tempTxt = itemView.txt_temp
        val windTxt = itemView.txt_wind
    }
}