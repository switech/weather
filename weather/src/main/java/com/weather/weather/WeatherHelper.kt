package com.weather.weather

import android.content.Context
import com.weather.app.Constants
import com.weather.retrofit.ApiServiceProvider
import com.weather.weather.callback.TodayWeather
import com.weather.weather.callback.ForecastWeatherCallback
import com.weather.weather.model.CurrentWeather
import com.weather.weather.model.ForecastData
import java.lang.Exception

class WeatherHelper(val context: Context, appId: String) {

    private var requestData = HashMap<String, String>()

    init {
        requestData[Constants.PARAM.APP_ID] = appId
    }

    /**
     * API call for get current weather by lat long...
     */
    fun getCurrentWeatherByLatLong(latitude: Double, longitude: Double, units: String, callback: TodayWeather) {
        requestData[Constants.PARAM.LATITUDE] = latitude.toString()
        requestData[Constants.PARAM.LONGITUDE] = longitude.toString()
        requestData[Constants.PARAM.UNITS] = units

        ApiServiceProvider.getInstance(context).getCurrentWeatherByLatLong(requestData) { response ->
            try {
                callback.onSuccess(response.body() as CurrentWeather)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    /**
     * API call for get forecast weather by lat long...
     */
    fun getForecastWeatherByLatLong(latitude: Double, longitude: Double, units: String, callback: ForecastWeatherCallback) {
        requestData[Constants.PARAM.LATITUDE] = latitude.toString()
        requestData[Constants.PARAM.LONGITUDE] = longitude.toString()
        requestData[Constants.PARAM.UNITS] = units

        ApiServiceProvider.getInstance(context).getForecastWeatherByLatLong(requestData) { response ->
            try {
                callback.onSuccess(response.body() as ForecastData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}