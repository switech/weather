package com.weather.retrofit

import android.content.Context
import com.weather.app.Constants
import com.weather.retrofit.listener.RetrofitListener
import com.weather.weather.model.CurrentWeather
import com.weather.retrofit.utils.ConnectionError
import com.weather.weather.model.ForecastData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class ApiServiceProvider : RetrofitBase {
    private var apiServices: ApiServices

    //private LoadingDialog mDialog;
    private constructor(context: Context) : super(context, Constants.UrlPath.BASE_URL, true) {
        //mDialog = new LoadingDialog(context);
        apiServices = retrofit.create(ApiServices::class.java)
    }

    private constructor(context: Context, baseUrl: String) : super(context, baseUrl, true) {
        //mDialog = new LoadingDialog(context);
        apiServices = retrofit.create(ApiServices::class.java)
    }

    private fun manageResponse(response: Response<*>, retrofitListener: RetrofitListener) {
        when (response.code()) {
            Constants.ResponseCode.CODE_200, Constants.ResponseCode.CODE_204 -> validateResponse(
                response,
                retrofitListener
            )
            Constants.ResponseCode.CODE_500, Constants.ResponseCode.CODE_400, Constants.ResponseCode.CODE_403, Constants.ResponseCode.CODE_404 -> {}
            Constants.ResponseCode.CODE_401 -> {}
        }
    }

    private fun dismissDialog() {
        /*if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();*/
    }

    /**
     * Used to get list of country...
     * @param retrofitListener
     */
    fun getCurrentWeatherByLatLong(
        requestData: HashMap<String?, String?>?,
        retrofitListener: RetrofitListener
    ) {
        /*if (isProgressShown) {
            mDialog.show();
        }*/
        val call = apiServices.getCurrentWeatherByLatLong(requestData)
        call!!.enqueue(object : Callback<CurrentWeather?> {
            override fun onResponse(
                call: Call<CurrentWeather?>,
                response: Response<CurrentWeather?>
            ) {
                dismissDialog()
                manageResponse(response, retrofitListener)
            }

            override fun onFailure(call: Call<CurrentWeather?>, t: Throwable) {
                dismissDialog()
                t.printStackTrace()
                ConnectionError(context, t).setListener {
                    getCurrentWeatherByLatLong(
                        requestData,
                        retrofitListener
                    )
                }
                //retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.UrlPath.IS_EMAIL_EXISTS);
            }
        })
    }

    fun getForecastWeatherByLatLong(
        requestData: HashMap<String?, String?>?,
        retrofitListener: RetrofitListener
    ) {
        /*if (isProgressShown) {
            mDialog.show();
        }*/
        val call = apiServices.getForecastWeatherByLatLong(requestData)
        call!!.enqueue(object : Callback<ForecastData?> {
            override fun onResponse(call: Call<ForecastData?>, response: Response<ForecastData?>) {
                dismissDialog()
                manageResponse(response, retrofitListener)
            }

            override fun onFailure(call: Call<ForecastData?>, t: Throwable) {
                dismissDialog()
                t.printStackTrace()
                ConnectionError(context, t).setListener {
                    getForecastWeatherByLatLong(
                        requestData,
                        retrofitListener
                    )
                }
                //retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.UrlPath.IS_EMAIL_EXISTS);
            }
        })
    }

    companion object {
        fun getInstance(context: Context): ApiServiceProvider {
            return ApiServiceProvider(context)
        }

        fun getInstance(context: Context, baseUrl: String): ApiServiceProvider {
            return ApiServiceProvider(context, baseUrl)
        }
    }
}