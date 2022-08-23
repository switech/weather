package com.weather.weather.model

import com.google.gson.annotations.SerializedName
import com.weather.weather.model.Weather
import com.weather.weather.model.Wind

class ForecastWeather {
    @SerializedName("dt")
    var dt: Long? = null

    @SerializedName("main")
    var main: Main? = null

    @SerializedName("weather")
    var weather: List<Weather>? = null

    @SerializedName("wind")
    var wind: Wind? = null

    @SerializedName("visibility")
    val visibility: Long? = null

    @SerializedName("pop")
    val pop: Double? = null

    @SerializedName("dt_txt")
    var dtTxt: String? = null
}