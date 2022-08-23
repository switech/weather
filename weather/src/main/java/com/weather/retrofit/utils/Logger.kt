package com.weather.retrofit.utils

import android.content.Context
import java.io.Serializable

class Logger : Serializable {
    private var tag: String? = null

    private constructor() {}
    constructor(cls: Class<*>) {
        tag = cls.name
    }

    constructor(tag: String?) {
        this.tag = tag
    }

    /**
     * Prints the stack trace for the given throwable instance for debug build.
     *
     * @param ex
     */
    fun error(ex: Throwable?) {
        LogUtil.error(tag, "", ex)
    }

    fun warn(log: String?) {
        LogUtil.warn(tag, log)
    }

    fun debug(log: String?) {
        LogUtil.debug(tag, log)
    }

    companion object {
        /**
         * Initializes logger. Logs are disabled for release builds
         * during initialization.
         *
         * @param context
         */
        fun init(context: Context?) {
            LogUtil.init(context)
        }
    }
}