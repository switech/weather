package com.weather.retrofit

import android.content.Context
import okhttp3.logging.HttpLoggingInterceptor.setLevel
import okhttp3.OkHttpClient.newBuilder
import okhttp3.OkHttpClient.Builder.addInterceptor
import okhttp3.OkHttpClient.Builder.readTimeout
import okhttp3.OkHttpClient.Builder.connectTimeout
import okhttp3.OkHttpClient.Builder.build
import okhttp3.OkHttpClient.Builder.interceptors
import okhttp3.Interceptor.Chain.request
import okhttp3.Request.newBuilder
import okhttp3.Request.Builder.addHeader
import okhttp3.Request.Builder.build
import okhttp3.Interceptor.Chain.proceed
import okhttp3.ResponseBody.string
import retrofit2.Retrofit
import okhttp3.Interceptor
import kotlin.Throws
import com.weather.retrofit.listener.RetrofitListener
import com.google.gson.Gson
import com.weather.BuildConfig
import com.weather.app.Constants
import com.weather.retrofit.model.ErrorObject
import com.weather.retrofit.utils.HttpUtil
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import com.weather.retrofit.RetrofitBase
import com.weather.retrofit.utils.Logger
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

open class RetrofitBase(protected var context: Context, baseUrl: String, addTimeout: Boolean) {
    @JvmField
    protected var retrofit: Retrofit
    private val logger: Logger
    private fun addVersioningHeaders(builder: Builder, context: Context) {
        val appVersion = "v.1.0.1"
        //final int version = AppUtils.INSTANCE.getApplicationVersionCode(context);
        val appName = "RetroKit"
        val name = "RetroKit"
        builder.interceptors().add(Interceptor { chain ->
            val request =
                chain.request().newBuilder() //.addHeader(appVersion, String.valueOf(version))
                    .addHeader(
                        appName,
                        name
                    ) //.addHeader("Authorization", "Bearer " + mSessionManager.getAuthToken(SessionManager.AUTH_TOKEN))
                    .build()
            chain.proceed(request)
        })
    }

    fun validateResponse(
        response: Response<*>,
        retrofitListener: RetrofitListener,
        apiFlag: String
    ) {
        if (response.code() == 200) {
            try {
                retrofitListener.onResponseSuccess(response)
            } catch (e: Exception) {
                error(response, retrofitListener, apiFlag)
            }
        } else {
            error(response, retrofitListener, apiFlag)
        }
    }

    fun validateResponse(response: Response<*>, retrofitListener: RetrofitListener) {
        if (response.code() == 200 || response.code() == 204) {
            try {
                retrofitListener.onResponseSuccess(response)
            } catch (e: Exception) {
                error(response, retrofitListener)
            }
        } else {
            error(response, retrofitListener)
        }
    }

    private fun error(response: Response<*>, retrofitListener: RetrofitListener, apiFlag: String) {
        val gson = Gson()
        var errorPojo: ErrorObject?
        try {
            errorPojo = gson.fromJson(response.errorBody()!!.string(), ErrorObject::class.java)
            if (errorPojo == null) {
                errorPojo = HttpUtil.getServerErrorPojo(context)
            }
            //retrofitListener.onResponseError(errorPojo, null, apiFlag);
        } catch (e: Exception) {
            //retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), null, apiFlag);
        }
    }

    private fun error(response: Response<*>, retrofitListener: RetrofitListener) {
        val gson = Gson()
        var errorPojo: ErrorObject?
        try {
            errorPojo = gson.fromJson(response.errorBody()!!.string(), ErrorObject::class.java)
            if (errorPojo == null) {
                errorPojo = HttpUtil.getServerErrorPojo(context)
            }
            //retrofitListener.onResponseError(errorPojo, null, apiFlag);
        } catch (e: Exception) {
            //retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), null, apiFlag);
        }
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val httpClientBuilder: Builder = OkHttpClient().newBuilder().addInterceptor(interceptor)
        if (addTimeout) {
            httpClientBuilder.readTimeout(Constants.TimeOut.SOCKET_TIME_OUT, TimeUnit.SECONDS)
            httpClientBuilder.connectTimeout(
                Constants.TimeOut.CONNECTION_TIME_OUT,
                TimeUnit.SECONDS
            )
        } else {
            httpClientBuilder.readTimeout(
                Constants.TimeOut.IMAGE_UPLOAD_SOCKET_TIMEOUT,
                TimeUnit.SECONDS
            )
            httpClientBuilder.connectTimeout(
                Constants.TimeOut.IMAGE_UPLOAD_CONNECTION_TIMEOUT,
                TimeUnit.SECONDS
            )
        }
        if (baseUrl.contains("https://api.openweathermap.org/")) addVersioningHeaders(
            httpClientBuilder,
            context
        )
        val httpClient: OkHttpClient = httpClientBuilder.build()
        logger = Logger(RetrofitBase::class.java.simpleName)
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
}