package com.weather.retrofit.listener

interface InternetConnectionListener {
    fun onConnectionError(errorMsg: String?)
}