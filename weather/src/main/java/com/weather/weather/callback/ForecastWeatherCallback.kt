package com.weather.weather.callback

import com.weather.weather.model.ForecastData

interface ForecastWeatherCallback {
    fun onSuccess(forecastData: ForecastData?)
}