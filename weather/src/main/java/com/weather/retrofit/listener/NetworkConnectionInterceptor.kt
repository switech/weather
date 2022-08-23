package com.weather.retrofit.listener

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.Throws

abstract class NetworkConnectionInterceptor : Interceptor {
    abstract val isInternetAvailable: Boolean
    abstract fun onInternetUnavailable(errorMsg: String?)
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (!isInternetAvailable) {
            onInternetUnavailable("Oops!! It looks like you are not connected with internet, please check your internet connection and try again.")
        }
        try {
            return chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            if (e is SocketTimeoutException) onInternetUnavailable("Request time out, please retry your request.") else if (e is UnknownHostException) onInternetUnavailable(
                "The connection to the server was unsuccessful. Please retry your request."
            ) else onInternetUnavailable("Oops!! It looks like you are not connected with internet, please check your internet connection and try again.")
        } catch (e: UnknownHostException) {
            if (e is SocketTimeoutException) onInternetUnavailable("Request time out, please retry your request.") else if (e is UnknownHostException) onInternetUnavailable(
                "The connection to the server was unsuccessful. Please retry your request."
            ) else onInternetUnavailable("Oops!! It looks like you are not connected with internet, please check your internet connection and try again.")
        } catch (e: ConnectException) {
            if (e is SocketTimeoutException) onInternetUnavailable("Request time out, please retry your request.") else if (e is UnknownHostException) onInternetUnavailable(
                "The connection to the server was unsuccessful. Please retry your request."
            ) else onInternetUnavailable("Oops!! It looks like you are not connected with internet, please check your internet connection and try again.")
        }
        return chain.proceed(request)
    }
}