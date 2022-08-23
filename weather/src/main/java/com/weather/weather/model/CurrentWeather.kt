package com.weather.weather.model

import com.google.gson.annotations.SerializedName

class CurrentWeather {
    @SerializedName("coord")
    val coord: Coord? = null

    @SerializedName("weather")
    val weather: List<Weather>? = null

    @SerializedName("base")
    val base: String? = null

    @SerializedName("main")
    val main: Main? = null

    @SerializedName("visibility")
    val visibility: Long? = null

    @SerializedName("wind")
    val wind: Wind? = null

    @SerializedName("dt")
    val dt: Long? = null

    @SerializedName("timezone")
    val timezone: Long? = null

    @SerializedName("id")
    val id: Long? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("cod")
    val cod: Int? = null
}