package com.weather.retrofit.listener

import retrofit2.Response

interface RetrofitListener {
    fun onResponseSuccess(response: Response<*>?) //void onResponseError(ErrorObject errorObject, Throwable throwable, String apiFlag);
}