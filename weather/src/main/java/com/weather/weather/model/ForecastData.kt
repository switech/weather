package com.weather.weather.model

import com.google.gson.annotations.SerializedName
import com.weather.weather.model.ForecastWeather

class ForecastData {
    @SerializedName("cod")
    val cod: String? = null

    @SerializedName("message")
    val message = 0.0

    @SerializedName("cnt")
    val cnt = 0

    @SerializedName("list")
    val list: List<ForecastWeather>? = null
}