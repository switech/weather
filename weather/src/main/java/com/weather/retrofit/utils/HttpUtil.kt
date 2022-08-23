package com.weather.retrofit.utils

import android.content.Context
import org.json.JSONObject
import com.weather.retrofit.utils.HttpUtil
import com.weather.retrofit.model.ErrorObject
import com.google.gson.Gson
import com.weather.app.Constants
import java.lang.Exception

object HttpUtil {
    private val logger = Logger(
        HttpUtil::class.java.simpleName
    )

    /**
     * This method returns a Json object for handling Force update error
     *
     * @return
     */
    fun getServerErrorJsonObject(context: Context?): JSONObject {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Constants.ErrorClass.STATUS, 505)
            jsonObject.put(Constants.ErrorClass.CODE, 3000)
            jsonObject.put(Constants.ErrorClass.MESSAGE, "Server Error")
            jsonObject.put(Constants.ErrorClass.DEVELOPER_MESSAGE, "Server Error")
        } catch (e: Exception) {
            logger.error(e)
        }
        return jsonObject
    }

    /**
     * This method returns a Json object for handling Force update error
     *
     * @return
     */
    fun getServerErrorPojo(context: Context?): ErrorObject? {
        try {
            val gson = Gson()
            return gson.fromJson(
                getServerErrorJsonObject(context).toString(),
                ErrorObject::class.java
            )
        } catch (e: Exception) {
            logger.error(e)
        }
        return null
    }
}