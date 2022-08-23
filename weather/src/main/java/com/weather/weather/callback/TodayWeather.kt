package com.weather.weather.callback

import com.weather.weather.model.CurrentWeather

interface TodayWeather {
    fun onSuccess(currentWeather: CurrentWeather?)
}