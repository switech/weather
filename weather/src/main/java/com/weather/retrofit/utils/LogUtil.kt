package com.weather.retrofit.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log

internal object LogUtil {
    var ISDEBUG = true

    /**
     * Initializes `LogUtil.ISDEBUG`
     *
     * @param context - current context passed
     */
    fun init(context: Context) {
        val isDebuggable =
            ApplicationInfo.FLAG_DEBUGGABLE == context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        ISDEBUG = isDebuggable
    }

    /**
     * Prints given tag and text as debug log, only if in debug mode.
     *
     * @param tag  - Tag to be set for logs
     * @param text - Log message
     */
    fun debug(tag: String?, text: String?) {
        if (ISDEBUG) {
            Log.d(tag, text!!)
        }
    }

    /**
     * Prints given tag and text as warning log, only if in debug mode.
     *
     * @param tag  - Tag to be set for logs
     * @param text - Log message
     */
    fun warn(tag: String?, text: String?) {
        if (ISDEBUG) {
            Log.w(tag, text!!)
        }
    }

    /**
     * Prints given tag and text as error log, only if in debug mode.
     *
     * @param tag  - Tag to be set for logs
     * @param text - Log message
     */
    fun error(tag: String?, text: String?) {
        if (ISDEBUG) {
            Log.e(tag, text!!)
        }
    }

    /**
     * Prints given tag and exception as error log, only if in debug mode with message.
     *
     * @param tag - Tag to be set for logs
     * @param msg - Log message
     * @param ex  - Exception to be logged
     */
    fun error(tag: String?, msg: String?, ex: Throwable?) {
        if (ISDEBUG) {
            Log.e(tag, msg, ex)
        }
    }

    /**
     * Prints given tag and exception as error log without message
     *
     * @param tag - Tag to be set for logs
     * @param ex  - Exception to be logged
     */
    fun error(tag: String?, ex: Throwable?) {
        error(tag, "", ex)
    }
}