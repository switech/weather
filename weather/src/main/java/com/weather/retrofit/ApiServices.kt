package com.weather.retrofit

import com.weather.app.Constants
import retrofit2.http.GET
import com.weather.weather.model.CurrentWeather
import com.weather.weather.model.ForecastData
import retrofit2.Call
import retrofit2.http.QueryMap
import java.util.HashMap

interface ApiServices {
    // Get list of country...
    @GET(Constants.UrlPath.CURRENT_WEATHER)
    fun getCurrentWeatherByLatLong(@QueryMap requestData: HashMap<String?, String?>?): Call<CurrentWeather?>?

    @GET(Constants.UrlPath.FORECAST_WEATHER)
    fun getForecastWeatherByLatLong(@QueryMap requestData: HashMap<String?, String?>?): Call<ForecastData?>?
}